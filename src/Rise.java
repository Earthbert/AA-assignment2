import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Rise extends Task{
    int nrOwnedCards;
    int nrWantedCards;
    int nrSets;

    String answer = "False";
    ArrayList<String> ownedCards = new ArrayList<>();
    ArrayList<String> wantedCards = new ArrayList<>();
    ArrayList<Integer>[] sets;
    ArrayList<Integer>[] containingSets;
    int[] selectedSets;

    HashMap<String, Integer> neededCardsIndex = new HashMap<>();

    @Override
    public void solve() throws IOException, InterruptedException {
        readProblemData();
        Trial trial = new Trial();
        trial.cardinalNr = neededCardsIndex.size();
        trial.nrSets = nrSets;
        trial.sets = sets;
        trial.containingSets = containingSets;
        for (int i = 1; i <= nrSets; i++) {
            trial.nrSelectedSets = i;
            trial.formulateOracleQuestion();
            trial.askOracle();
            trial.decipherOracleAnswer();
            if (trial.answer.equals("True")) {
                selectedSets = trial.selectedSets;
                answer = trial.answer;
                break;
            }
        }
        writeAnswer();
    }

    @Override
    public void readProblemData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] numbers = bufferedReader.readLine().split(" ");
        nrOwnedCards = Integer.parseInt(numbers[0]);
        nrWantedCards = Integer.parseInt(numbers[1]);
        nrSets = Integer.parseInt(numbers[2]);

        sets = new ArrayList[nrSets];

        for (int i = 0; i < nrOwnedCards; i++) {
            ownedCards.add(bufferedReader.readLine());
        }

        for (int i = 0; i < nrWantedCards; i++) {
            String card = bufferedReader.readLine();
            if (!ownedCards.contains(card))
                neededCardsIndex.putIfAbsent(card, neededCardsIndex.size() + 1);
            wantedCards.add(card);
        }

        containingSets = new ArrayList[neededCardsIndex.size()];
        for (int i = 0; i < neededCardsIndex.size(); i++) {
            containingSets[i] = new ArrayList<>();
        }

        for (int i = 0; i < nrSets; i++) {
            int nrCards = Integer.parseInt(bufferedReader.readLine());
            sets[i] = new ArrayList<>();
            for (int j = 0; j < nrCards; j++) {
                String card = bufferedReader.readLine();
                Integer index = neededCardsIndex.get(card);
                if (index != null) {
                    sets[i].add(index);
                    containingSets[index - 1].add(i + 1);
                }
            }
        }
        return;
    }

    @Override
    public void formulateOracleQuestion() throws IOException {
    }

    @Override
    public void decipherOracleAnswer() throws IOException {
    }

    @Override
    public void writeAnswer() throws IOException {
        if (answer.equals("True")) {
            System.out.println(selectedSets.length);
            for (final int selectedSet : selectedSets) {
                System.out.printf("%d ", selectedSet);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Rise().solve();
    }
}
