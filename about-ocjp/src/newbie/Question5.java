package newbie;

public class Question5 {

    public static void main(String[] args) {
        int[] lst = {1, 2, 3, 4, 5, 4, 3, 2, 1};
        int sum = 0;
        for (int frnt = 0, rear = lst.length - 1;
                frnt < 5 && rear >= 5; ++frnt, --rear) {
            System.out.println(frnt + "-" + rear);
            System.out.println(lst[frnt] + "-" + lst[rear]);
            sum = sum + lst[frnt] + lst[rear];
        }

        System.out.println(sum);

        // What is the result?
        // A. 20
        // B. 25
        // C. 29
        // D. Compilation fails
        // E. AnArrayIndexOutOfBoundsException is thrown at runtime

        // Answer: A
        // 首先 lst.length - 1 = 8
        // rear 8->5 最多进入四次循环体
        // frnt 0->4 最多进入五次循环体
        // 因为是与运算(&&), 所以最终只进入4次循环体

    }
}


