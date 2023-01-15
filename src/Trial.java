import java.io.*;
import java.util.ArrayList;

public class Trial extends Task {
    int cardinalNr;
    int nrSets;
    int nrSelectedSets;

    String answer;
    ArrayList<Integer>[] sets;
    ArrayList<Integer>[] containingSets;
    int[] selectedSets;

    @Override
    public void solve() throws IOException, InterruptedException {
        readProblemData();
        formulateOracleQuestion();
        askOracle();
        decipherOracleAnswer();
        writeAnswer();
    }

    @Override
    public void readProblemData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] numbers = bufferedReader.readLine().split(" ");
        cardinalNr = Integer.parseInt(numbers[0]);
        nrSets = Integer.parseInt(numbers[1]);
        nrSelectedSets = Integer.parseInt(numbers[2]);

        sets = new ArrayList[nrSets];
        containingSets = new ArrayList[cardinalNr];

        for (int i = 0; i < cardinalNr; i++) {
            containingSets[i] = new ArrayList<>();
        }

        for (int i = 0; i < nrSets; i++) {
            numbers = bufferedReader.readLine().split(" ");
            int nrElements = Integer.parseInt(numbers[0]);
            sets[i] = new ArrayList<>();
            for (int j = 0; j < nrElements; j++) {
                int element;
                element = Integer.parseInt(numbers[j + 1]);
                sets[i].add(element);
                containingSets[element - 1].add(i + 1);
            }
        }
    }

    @Override
    public void formulateOracleQuestion() throws IOException {
        File oracleInput = new File("sat.cnf");
        FileWriter fileWriter = new FileWriter(oracleInput);

        int nrEqu = nrSelectedSets + cardinalNr + nrSets + nrSelectedSets * nrSets * (nrSets - 1) / 2;
        fileWriter.write("p cnf " + nrSelectedSets * nrSets + " " + nrEqu + "\n");

        for (int i = 0; i < nrSelectedSets; i++) {
            for (int j = 1; j <= nrSets; j++) {
                fileWriter.write((j + i * nrSets) + " ");
            }
            fileWriter.write(0 + "\n");
        }

        for (int i = 1; i <= nrSets; i++) {
            for (int j = 0; j < nrSelectedSets; j++) {
                String string = -(i + j * nrSets) + " ";
                for (int k = j + 1; k < nrSelectedSets; k++) {
                    fileWriter.write(string + -(i + k * nrSets) + " 0\n");
                }
            }
        }

        for (int i = 0; i < nrSelectedSets; i++) {
            for (int j = 1; j <= nrSets; j++) {
                String string = -(j + i * nrSets) + " ";
                for (int k = j + 1; k <= nrSets; k++) {
                    fileWriter.write(string + -(k + i * nrSets) + " 0\n");
                }
            }
        }

        for (int i = 0; i < cardinalNr; i++) {
            for (int j = 0; j < nrSelectedSets; j++) {
                for (int k = 0; k < containingSets[i].size(); k++) {
                    fileWriter.write((containingSets[i].get(k) + j * nrSets) + " ");
                }
            }
            fileWriter.write("0\n");
        }

        fileWriter.close();
    }

    @Override
    public void decipherOracleAnswer() throws IOException {
        File oracleAnswer = new File("sat.sol");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(oracleAnswer));
        answer = bufferedReader.readLine();
        selectedSets = new int[nrSelectedSets];

        if (answer.equals("True")) {
            int nrVariables = Integer.parseInt(bufferedReader.readLine());
            String[] numbers = bufferedReader.readLine().split(" ");
            int j = 0;
            for (int i = 0; i < nrVariables; i++) {
                int variable = Integer.parseInt(numbers[i]);
                if (variable > 0) {
                    if (nrSets < variable)
                        selectedSets[j] = variable % nrSets;
                    else
                        selectedSets[j] = variable;
                    j++;
                }
            }
        }
    }

    @Override
    public void writeAnswer() throws IOException {
        System.out.println(answer);
        if (answer.equals("True")) {
            System.out.println(nrSelectedSets);
            for (int i = 0; i < nrSelectedSets; i++) {
                System.out.printf("%d ", selectedSets[i]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Trial().solve();
    }
}
