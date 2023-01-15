import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Rise extends Task{
    int nrOwnedCards;
    int nrWantedCards;
    int nrSets;

    String answer;
    ArrayList<Integer> ownedCards = new ArrayList<>();
    ArrayList<Integer> wantedCards = new ArrayList<>();
    ArrayList<Integer>[] sets;
    HashMap<Integer, ArrayList<Integer>> containingSets = new HashMap<>();
    int[] selectedSets;

    HashMap<String, Integer> cardIndex = new HashMap<>();

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
        nrOwnedCards = Integer.parseInt(numbers[0]);
        nrWantedCards = Integer.parseInt(numbers[1]);
        nrSets = Integer.parseInt(numbers[2]);

        sets = new ArrayList[nrSets];
        selectedSets = new int[nrSets];

        for (int i = 0; i < nrOwnedCards; i++) {
            String card = bufferedReader.readLine();
            cardIndex.computeIfAbsent(card, k -> cardIndex.size() + 1);
            ownedCards.add(cardIndex.get(card));
        }

        for (int i = 0; i < nrWantedCards; i++) {
            String card = bufferedReader.readLine();
            cardIndex.putIfAbsent(card, cardIndex.size() + 1);
            wantedCards.add(cardIndex.get(card));
        }

        for (int i = 0; i < nrSets; i++) {
            int nrCards = Integer.parseInt(bufferedReader.readLine());
            sets[i] = new ArrayList<>();
            for (int j = 0; j < nrCards; j++) {
                String card = bufferedReader.readLine();
                cardIndex.computeIfAbsent(card, k -> cardIndex.size() + 1);
                Integer index = cardIndex.get(card);
                if (wantedCards.contains(index) && !ownedCards.contains(index)) {
                    sets[i].add(index);
                    containingSets.computeIfAbsent(index, k -> new ArrayList<>());
                    ArrayList<Integer> sets = containingSets.get(index);
                    sets.add(i + 1);
                }
            }
        }
    }

    @Override
    public void formulateOracleQuestion() throws IOException {
    }

    @Override
    public void decipherOracleAnswer() throws IOException {
    }

    @Override
    public void writeAnswer() throws IOException {
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Rise().solve();
    }
}
