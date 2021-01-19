package ir.mushtu;

public class StringDistance {
    private final String s;
    private final String t;
    private final int[][] d;

    public StringDistance(String s, String t) {
        if (s == null || t == null)
            throw new IllegalArgumentException("null arguments");
        this.s = s;
        this.t = t;
        this.d = new int[s.length() + 1][t.length() + 1];
    }

    public int findDistance() {
        for (int i = 1; i <= s.length(); i++) {
            d[i][0] = i;
        }
        for (int j = 1; j <= t.length(); j++) {
            d[0][j] = j;
        }
        for (int j = 1; j <= t.length(); j++) {
            for (int i = 1; i <= s.length(); i++) {
                int subCost = 0;
                if (s.charAt(i - 1) != t.charAt(j - 1)) {
                    subCost = 1;
                }
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + subCost);
            }
        }
        return d[s.length()][t.length()];
    }
}

