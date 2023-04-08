/*
Програмне забезпечення високопродуктивних комп'ютерних систем
Лабораторна робота 1, Варіант 21
Завдання:
R = max(Z)*(B*MV) + e * X *(MM*MC)
T1 = MV, MC
T2 = MM, R
T3 = -
T4 = B,X, e, Z

Амелін В’ячеслав Олегович, ІП-05
Дата 08.04.2023
*/

public class App {
  public static long curTime = System.currentTimeMillis();
  public static void main(String[] args) {

    System.out.println("N = " + Data.N + "\n");

    T1 thread1 = new T1();
    T2 thread2 = new T2();
    T3 thread3 = new T3();
    T4 thread4 = new T4();

    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();
  }
}
