import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Trial extends Task {
    int cardinalNr;
    int nrSets;
    int chosenSets;

    int[][] sets;
    ArrayList<Integer>[] containingSets;

    @Override
    public void solve() throws IOException, InterruptedException {
        readProblemData();
        formulateOracleQuestion();
    }

    @Override
    public void readProblemData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] numbers = bufferedReader.readLine().split(" ");
        cardinalNr = Integer.parseInt(numbers[0]);
        nrSets = Integer.parseInt(numbers[1]);
        chosenSets = Integer.parseInt(numbers[2]);

        sets = new int[nrSets][];
        containingSets = new ArrayList[cardinalNr];

        for (int i = 0; i < cardinalNr; i++) {
            containingSets[i] = new ArrayList<>();
        }

        for (int i = 0; i < nrSets; i++) {
            numbers = bufferedReader.readLine().split(" ");
            int nrElements = Integer.parseInt(numbers[0]);
            sets[i] = new int[nrElements];
            for (int j = 0; j < nrElements; j++) {
                int element;
                element = Integer.parseInt(numbers[j + 1]);
                sets[i][j] = element;
                containingSets[element].add(i);
            }
        }

        for (int[] set : sets) {
            System.out.println();
            for (int value : set) {
                System.out.printf("%d ", value);
            }
        }
    }

    @Override
    public void formulateOracleQuestion() throws IOException {
        File oracleInput = new File("sat.cnf");
        FileWriter fileWriter = new FileWriter(oracleInput);

        int nrEqu = chosenSets + cardinalNr + nrSets + chosenSets * nrSets * (nrSets - 1) / 2;
        fileWriter.write("p cnf " + 2 * nrSets + " " + nrEqu + "\n");

        for (int i = 0; i < chosenSets; i++) {
            for (int j = 1; j <= nrSets; j++) {
                fileWriter.write((j + i * nrSets) + " ");
            }
            fileWriter.write(0 + "\n");
        }

        for (int i = 1; i <= nrSets; i++) {
            for (int j = 0; j < chosenSets; j++) {
                fileWriter.write(-(i + j * nrSets) + " ");
            }
            fileWriter.write(0 + "\n");
        }

        for (int i = 0; i < chosenSets; i++) {
            for (int j = 1; j <= nrSets; j++) {
                String string = (-j - i * nrSets) + " ";
                for (int l = j + 1; l <= nrSets; l++) {
                    fileWriter.write(string + (-l - i * nrSets) + " 0\n");
                }
            }
        }

        for (int i = 0; i < cardinalNr; i++) {

        }

        fileWriter.close();
    }

    @Override
    public void decipherOracleAnswer() throws IOException {

    }

    @Override
    public void writeAnswer() throws IOException {

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Trial().solve();
    }
}
