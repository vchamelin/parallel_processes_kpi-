public class T3 extends Thread {
  private int z3_1; // max(Zh)
  private int z_3; // z
  private int[] B_3; // B
  private int[] X_3; // X
  private int e_3; // e

  @Override
  public void run() {

      try {
          System.out.println("3: Старт T3");
          System.out.println("3: T3 чекає на дозвіл T1 T2 T4 про введення даних");
          Data.SemInput_T1.acquire();
          Data.SemInput_T2.acquire();
          Data.SemInput_T4.acquire();
          System.out.println("3: T3 отримує дозвіл T1 Т2 T4");

          z3_1 = Data.maxSubArr(Data.Z, Data.H * 2, Data.H * 3);
          Data.A1.set(Data.setMax(Data.A1, z3_1));

          Data.SemEndZ_T3.release(1);

          try {
              System.out.println("3: T3 чекає на EndZ_T[1, 2, 4]");
              Data.SemEndZ_T1.acquire();
              System.out.println("3: T3 отримало EndZ_T[1, 2, 4]");

              z_3 = Data.A1.get();

              synchronized (Data.CS1) {
                B_3 = Data.B;
              }

              Data.B1.lock();
              try {
                  e_3 = Data.e;
              } finally {
                  Data.B1.unlock();
              }


              int[] mulMtxVec1 = Data.mulMtxAndVec(Data.MV, Data.B);
              int[][] mulMtxVec2 = Data.mulSubMtx(Data.MM, Data.MC, Data.H * 3, Data.N);
              int[] part1 = Data.mulVecAndScalar(mulMtxVec1, Data.A1.get());
              int[] part2 = Data.mulMtxAndVec(mulMtxVec2, Data.X);
              int[] part3 = Data.mulVecAndScalar(part2, Data.e);
              int[] R_1 = Data.sumVecs(part1, part3);
              Data.saveToSubVector(R_1, Data.R, Data.H * 2, Data.H * 3);

              Data.SemEnd_T3.release(1);

              System.out.println("3: T3 stopped");

          } catch (Exception e) {
              throw new RuntimeException(e);
          }


      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }
  }

}
