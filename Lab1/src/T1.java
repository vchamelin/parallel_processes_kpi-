public class T1 extends Thread {
  private int z1_1; // max(Zh)
  private int z_1; // z
  private int[] B_1; // B
  private int[] X_1; // X
  private int e_1; // e

  @Override
  public void run() {
    try {
      System.out.println("1: Старт T1");

      System.out.println("1: Ввід матриці MV");
      Data.MV = Data.insertMtxWithNum(1);

      System.out.println("1: Ввід матриці MC");
      Data.MC = Data.insertMtxWithNum(1);

      System.out.println("1: T1 звільнює дозвіл для інших семафорів");
      Data.SemInput_T1.release(3);

      try {
        System.out.println("1: T1 чекає на дозвіл T2 T4 про введення даних");
        Data.SemInput_T2.acquire();
        Data.SemInput_T4.acquire();
        System.out.println("1: T1 отримує дозвіл T2 T4");

        z1_1 = Data.maxSubArr(Data.Z, 0, Data.H);
        Data.A1.set(Data.setMax(Data.A1, z1_1));

        Data.SemEndZ_T2.release(1);

        try {
          System.out.println("1: T1 чекає на EndZ_T[2, 3, 4]");
          Data.SemEndZ_T1.acquire();
          System.out.println("1: T1 отримало EndZ_T[2, 3, 4]");

          z_1 = Data.A1.get();

          synchronized (Data.CS1) {
            B_1 = Data.B;
          }

          Data.B1.lock();
          try {
            e_1 = Data.e;
          } finally {
            Data.B1.unlock();
          }

          int[] mulMtxVec1 = Data.mulMtxAndVec(Data.MV, Data.B);
          int[][] mulMtxVec2 = Data.mulSubMtx(Data.MM, Data.MC, Data.H * 3, Data.N);
          int[] part1 = Data.mulVecAndScalar(mulMtxVec1, Data.A1.get());
          int[] part2 = Data.mulMtxAndVec(mulMtxVec2, Data.X);
          int[] part3 = Data.mulVecAndScalar(part2, Data.e);
          int[] R_1 = Data.sumVecs(part1, part3);
          Data.saveToSubVector(R_1, Data.R, 0, Data.H);

          Data.SemEnd_T1.release(1);

          System.out.println("1: T1 stopped");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }
    } catch (RuntimeException e) {
      throw new RuntimeException(e);
    }
  }
}
