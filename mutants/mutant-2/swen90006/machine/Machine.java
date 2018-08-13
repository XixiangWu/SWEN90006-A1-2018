package swen90006.machine;

import java.util.Arrays;
import java.util.List;

public class Machine 
{
  /** arithmetic instructions each take three registers as arguments with the
   *  destination register appearing first
   */

  /** add rd rs1 rs2   =~   rd = rs1 + rs2 */
  public static final String INSTRUCTION_ADD = "add";

  /** sub rd rs1 rs2   =~   rd = rs1 - rs2 */    
  public static final String INSTRUCTION_SUBTRACT = "sub";

  /** mul rd rs1 rs2   =~   rd = rs1 * rs2 */        
  public static final String INSTRUCTION_MULT = "mul";

  /** div rd rs1 rs2   =~   rd = rs1 / rs2 */            
  public static final String INSTRUCTION_DIVIDE = "div";

  /** ret rs           =~   return rs */
  public static final String INSTRUCTION_RETURN = "ret";

  /** ldr rd rs offs  =~    rd = rs[offs] */
  public static final String INSTRUCTION_LOAD = "ldr";

  /** str ra offs rb   =~    ra[offs] = rb */
  public static final String INSTRUCTION_STORE = "str";

  /** mov rd val       =~    rd = val */
  public static final String INSTRUCTION_MOVE = "mov";

  /** jmp offs         =~    pc = pc + offs */
  public static final String INSTRUCTION_JUMP = "jmp";

  /** jz ra offs       =~   if (ra == 0) pc = pc + offs else pc = pc + 1 */
  public static final String INSTRUCTION_JZ = "jz";
    
  public static final int NUM_REGS = 32;
  public static final int MAX_REG = (NUM_REGS - 1);
  public static final int MEMORY_SIZE = 65536; /* 4 x as much memory as a 64 */
  public static final int MAX_ADDR = MEMORY_SIZE-1;
    
  private int[] memory;
  private int[] regs;

  private int count = 0; /* counts number of instructions executed so far */
    
  public Machine()
  {
    memory = new int[MEMORY_SIZE];
    regs = new int[NUM_REGS];
    count = 0;
  }

  private void do_add(int dest, int src1, int src2)
  {
    regs[dest] = regs[src1] + regs[src2];
  }
    
  private void do_sub(int dest, int src1, int src2)
  {
    regs[dest] = regs[src1] - regs[src2];
  }
    
  private void do_mult(int dest, int src1, int src2)
  {
    regs[dest] = regs[src1] * regs[src2];
  }
    
  private void do_div(int dest, int src1, int src2)
  {
    if (regs[src2] == 0){
      /* no op */
    }else{
      regs[dest] = regs[src1] / regs[src2];
    }
  }

  private void do_load(int dest, int src, int offs) {
    if (regs[src] + offs > MAX_ADDR){
      /* no op */
    }else if(regs[src] + offs < 0){
      /* no op */
    }else{
      regs[dest] = memory[regs[src] + offs];
    }
  }

  private void do_store(int a, int offs, int b)  {
    if (regs[a] + offs > MAX_ADDR){
      /* no op */
    }else if(regs[a] + offs < 0){
      /* no op */
    }else{
      memory[regs[a] + offs] = regs[b];
    }
  }

  private void do_move(int rd, int val){
    regs[rd] = val;
  }

  private int parseReg(String s) throws InvalidInstructionException
  {
    if (s.length() < 2){
      throw new InvalidInstructionException();
    }
    if (s.charAt(0) != 'r'){
      throw new InvalidInstructionException();
    }
    String numstr = s.substring(1);
    int num = 0;
    try {
      num = Integer.parseInt(numstr);
    } catch (Exception e){
      throw new InvalidInstructionException();
    }
    validate_reg(num);
    return num;
  }

  private int parseOffset(String s)
    throws InvalidInstructionException
  {
    int num = 0;
    try {
      num = Integer.parseInt(s);
    } catch (Exception e){
      throw new InvalidInstructionException();
    }
    validate_offset(num);
    return num;
  }


  private void validate_reg(int reg)
    throws InvalidInstructionException
  {
    if (reg < 0 || reg > MAX_REG) {
      throw new InvalidInstructionException();
    }
  }

  private void validate_offset(int offset)
    throws InvalidInstructionException
  {
    if (offset < -MAX_ADDR || offset > MAX_ADDR) {
      throw new InvalidInstructionException();
    }
  }
    
  /** Execute an assembly program.
   *
   * @param prog is the program to execute as an iterable collection of strings, 
   *        each of which is a single instruction.
   * @return the program's return value.
   * @throws Exception when program has unrecognised or 
   *         invalid instructions, or when it returns no result when it finishes
   */
  int execute(List<String> instructions) 
    throws InvalidInstructionException,
	   NoReturnValueException
  {

    int instructionsExecuted = 0;
    int pc = 0;
    final int progLength = instructions.size();
    while(true){
      if (pc < 0 || pc >= progLength){
	/* will cause NoReturnValueException to be thrown
	 * but that is not a bug and and indeed is what the
	 * VM is supposed to do if the pc becomes negative,
	 * since in this case the program's execution
	 * finishes early without a return value having
	 * been produced. */
	break;
      }
      String inst = instructions.get(pc);
      /* strip leading and trailing whitespace */ 
      inst = inst.toLowerCase().replaceAll("^\\s+","").replaceAll("\\s+$","");
      /* strip out any comments */
      String[] toks = inst.split(";");
      inst = toks[0];

      /* check for blank lines */
      if (inst.equals("")){
	pc = pc + 1;
	count++;
	continue;
      }

      instructionsExecuted++;
      /* now  tokenize by splitting on whitespace */
      toks = inst.split("\\s+");

      /* check minimum number of tokens */
      if (toks.length < 2){
	throw new InvalidInstructionException();
      }

      if (toks[0].equals(INSTRUCTION_ADD)){
	if (toks.length != 4){
	  throw new InvalidInstructionException();
	}
	int rd = parseReg(toks[1]);
	int rs1 = parseReg(toks[2]);
	int rs2 = parseReg(toks[3]);
	do_add(rd,rs1,rs2);
      } else if (toks[0].equals(INSTRUCTION_SUBTRACT)){
	if (toks.length != 4){
	  throw new InvalidInstructionException();
	}
	int rd = parseReg(toks[1]);
	int rs1 = parseReg(toks[2]);
	int rs2 = parseReg(toks[3]);
	do_sub(rd,rs1,rs2);
      } else if (toks[0].equals(INSTRUCTION_MULT)){
	if (toks.length != 4){
	  throw new InvalidInstructionException();
	}
	int rd = parseReg(toks[1]);
	int rs1 = parseReg(toks[2]);
	int rs2 = parseReg(toks[3]);
	do_mult(rd,rs1,rs2);
      } else if (toks[0].equals(INSTRUCTION_DIVIDE)){
	if (toks.length != 4){
	  throw new InvalidInstructionException();
	}
	int rd = parseReg(toks[1]);
	int rs1 = parseReg(toks[2]);
	int rs2 = parseReg(toks[3]);
	do_div(rd,rs1,rs2);
      } else if (toks[0].equals(INSTRUCTION_RETURN)){
	int rs = parseReg(toks[1]);
	count++;
	return regs[rs];
      } else if (toks[0].equals(INSTRUCTION_LOAD)){
	if (toks.length != 4){
	  throw new InvalidInstructionException();
	}
	int rd = parseReg(toks[1]);
	int rs = parseReg(toks[2]);
	int offs = parseOffset(toks[3]);
	do_load(rd,rs,offs);
      } else if (toks[0].equals(INSTRUCTION_STORE)){
	if (toks.length != 4){
	  throw new InvalidInstructionException();
	}
	int ra = parseReg(toks[1]);
	int offs = parseOffset(toks[2]);
	int rb = parseReg(toks[3]);
	do_store(ra,offs,rb);
      } else if (toks[0].equals(INSTRUCTION_MOVE)){
	if (toks.length != 3){
	  throw new InvalidInstructionException();
	}
	int rd = parseReg(toks[1]);
	int offs = parseOffset(toks[2]);
	do_move(rd,offs);
      } else if (toks[0].equals(INSTRUCTION_JUMP)){
	if (toks.length != 2){
	  throw new InvalidInstructionException();
	}
	int offs = parseOffset(toks[1]);
	pc  = pc + offs;
	count++;
	continue; /* avoid default increment of pc below */
      } else if (toks[0].equals(INSTRUCTION_JZ)){
	if (toks.length != 3){
	  throw new InvalidInstructionException();
	}
	int ra = parseReg(toks[1]);
	int offs = parseOffset(toks[2]);
	if (regs[ra] == 0){
	  pc = pc + offs;
	}else{
	  pc = pc + 1;
	}
	count++;
	continue; /* avoid default increment the pc below */
      } else {
	System.err.println("Unrecognised instruction: " + inst);
	throw new InvalidInstructionException();
      }
      count++;
      pc = pc + 1;
    }

    /* got here without returning already... */
    throw new NoReturnValueException();
  }

  /**
   * get the number of instructions successfully executed by the VM so far
   */
  public int getCount(){
    return count;
  }
}
