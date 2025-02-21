import java.util.Arrays;

/*
프로그래머스 / 에어컨 / Level 3
https://school.programmers.co.kr/learn/courses/30/lessons/214289
 */
class PGS_214289 {

    private static final int MAX = 1_000_001;
    private static final int MAX_TEMP = 40;
    private static final int MIN_TEMP = -10;
    private static final int RANGE = MAX_TEMP - MIN_TEMP;

    public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {

        if (t1 <= temperature && temperature <= t2) {
            return 0;
        }

        temperature -= MIN_TEMP;
        t1 -= MIN_TEMP;
        t2 -= MIN_TEMP;

        int N = onboard.length;
        int[][] dp = new int[N][RANGE + 1];

        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], MAX);
        }

        dp[0][temperature] = 0;

        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j <= RANGE; j++) {

                if (onboard[i] == 1 && (j < t1 || j > t2)) {
                    continue;
                }

                int nextTemp = j;

                if (j < temperature && j < RANGE) {
                    nextTemp = j + 1;
                } else if (j > temperature && j > 0) {
                    nextTemp = j - 1;
                }

                dp[i + 1][nextTemp] = Math.min(dp[i][j], dp[i + 1][nextTemp]);

                if (j > 0) {
                    dp[i + 1][j - 1] = Math.min(dp[i][j] + a, dp[i + 1][j - 1]);
                }
                if (j < RANGE) {
                    dp[i + 1][j + 1] = Math.min(dp[i][j] + a, dp[i + 1][j + 1]);
                }

                dp[i + 1][j] = Math.min(dp[i][j] + b, dp[i + 1][j]);
            }
        }

        int answer = MAX;
        for (int j = 0; j <= RANGE; j++) {
            if (onboard[onboard.length - 1] == 1 && (j < t1 || j > t2)) {
                continue;
            }
            answer = Math.min(dp[onboard.length - 1][j], answer);
        }

        return answer;
    }
}
