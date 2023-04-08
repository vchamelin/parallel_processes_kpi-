public class T2 extends Thread {
  private int z2_1; // max(Zh)
  private int z_2; // z
  private int[] B_2; // B
  private int[] X_2; // X
  private int e_2; // e

  @Override
  public void run() {
    System.out.println("2: Старт T2");

    System.out.println("2: Ввід матриці MM");
    Data.MM = Data.insertMtxWithNum(1);

    System.out.println("2: T2 звільнює дозвіл для інших семафорів");
    Data.SemInput_T2.release(3);

    try {
      System.out.println("2: T2 чекає на дозвіл T1 T4 про введення даних");
      Data.SemInput_T1.acquire();
      Data.SemInput_T4.acquire();
      System.out.println("2: T2 отримує дозвіл T1 T4");

      z2_1 = Data.maxSubArr(Data.Z, 0, Data.H);
      Data.A1.set(Data.setMax(Data.A1, z2_1));

      Data.SemEndZ_T1.release(3);

      System.out.println("2: T2 чекає на EndZ_T[1, 3, 4]");
      Data.SemEndZ_T2.acquire();
      Data.SemEndZ_T3.acquire();
      Data.SemEndZ_T4.acquire();
      System.out.println("2: T2 отримало EndZ_T[1, 3, 4]");

      z_2 = Data.A1.get();

      synchronized (Data.CS1) {
          B_2 = Data.B;
      }

      Data.B1.lock();
      try {
          e_2 = Data.e;
      } finally {
          Data.B1.unlock();
      }

      int[] mulMtxVec1 = Data.mulMtxAndVec(Data.MV, Data.B);
      int[][] mulMtxVec2 = Data.mulSubMtx(Data.MM, Data.MC, Data.H * 3, Data.N);
      int[] part1 = Data.mulVecAndScalar(mulMtxVec1, Data.A1.get());
      int[] part2 = Data.mulMtxAndVec(mulMtxVec2, Data.X);
      int[] part3 = Data.mulVecAndScalar(part2, Data.e);
      int[] R_1 = Data.sumVecs(part1, part3);
      Data.saveToSubVector(R_1, Data.R, Data.H, Data.H * 2);

      System.out.println("2: T2 чекає на дозволи T1 T3 T4");
      Data.SemEnd_T1.acquire();
      Data.SemEnd_T3.acquire();
      Data.SemEnd_T4.acquire();
      System.out.println("2: T2 отримує дозволи T1 T3 T4");

      System.out.println("2: Вивід результату R:");
      Data.printVector(Data.R);

      long finishTime = System.currentTimeMillis() - App.curTime;
      System.out.println("N = "+ Data.N);
      System.out.println("Time of execution: " + finishTime);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
  }
}
