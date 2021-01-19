package ir.mushtu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String CSV_SEPARATOR = ";";

    public static void main(String[] args) throws IOException {
        String csv1Path = args[0];
        int csv1Col = Integer.valueOf(args[1]);
        String csv2Path = args[2];
        int csv2Col = Integer.valueOf(args[3]);
        String csvOutPath = args[4];
        String csv1Header;
        String csv2Header;
        List<String[]> csv1DataRows = new ArrayList<>();
        List<String[]> csv2DataRows = new ArrayList<>();
        List<String[]> csvOutDataRows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csv1Path))) {
            csv1Header = br.readLine();
            for (String line; (line = br.readLine()) != null; ) {
                csv1DataRows.add(line.split(CSV_SEPARATOR));
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(csv2Path))) {
            csv2Header = br.readLine();
            for (String line; (line = br.readLine()) != null; ) {
                csv2DataRows.add(line.split(CSV_SEPARATOR));
            }
        }
        for (String[] csv1DataRow : csv1DataRows) {
            int minDis = Integer.MAX_VALUE;
            int matchIndex = -1;
            for (int j = 0; j < csv2DataRows.size(); j++) {
                int dis = new StringDistance(csv1DataRow[csv1Col], csv2DataRows.get(j)[csv2Col]).findDistance();
                if (dis <= minDis) {
                    minDis = dis;
                    matchIndex = j;
                }
            }
            csvOutDataRows.add(concatArray(csv1DataRow, csv2DataRows.get(matchIndex)));
        }
        // save to file
        try (FileWriter fw = new FileWriter(csvOutPath)) {
            fw.write(csv1Header + CSV_SEPARATOR + csv2Header + "\n");
            for (int k = 0; k < csvOutDataRows.size(); k++) {
                String[] row = csvOutDataRows.get(k);
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    line.append(row[i]).append(CSV_SEPARATOR);
                }
                fw.write(line.toString());
                fw.write("\n");
            }
        }
    }

    static String[] concatArray(String[] first, String[] second) {
        int lenArray1 = first.length;
        int lenArray2 = second.length;
        String[] result = new String[lenArray1 + lenArray2];
        System.arraycopy(first, 0, result, 0, lenArray1);
        System.arraycopy(second, 0, result, lenArray1, lenArray2);
        return result;
    }
}
