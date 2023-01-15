import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Redemption extends Task {
    ArrayList<LinkedList<Integer>> sets = new ArrayList<>();
    ArrayList<String> ownedCards = new ArrayList<>();
    ArrayList<String> wantedCards = new ArrayList<>();
    HashMap<String, Integer> neededCardsIndex = new HashMap<>();

    ArrayList<ArrayList<Integer>> containingSets = new ArrayList<>();

    ArrayList<Integer> selectedSets = new ArrayList<>();

    @Override
    public void readProblemData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] numbers = bufferedReader.readLine().split(" ");
        int nrOwnedCards = Integer.parseInt(numbers[0]);
        int nrWantedCards = Integer.parseInt(numbers[1]);
        int nrSets = Integer.parseInt(numbers[2]);

        for (int i = 0; i < nrOwnedCards; i++) {
            ownedCards.add(bufferedReader.readLine());
        }

        for (int i = 0; i < nrWantedCards; i++) {
            String card = bufferedReader.readLine();
            if (!ownedCards.contains(card))
                neededCardsIndex.putIfAbsent(card, neededCardsIndex.size() + 1);
            wantedCards.add(card);
        }

        for (int i = 0; i < neededCardsIndex.size(); i++) {
            containingSets.add(new ArrayList<>());
        }

        for (int i = 0; i < nrSets; i++) {
            int nrCards = Integer.parseInt(bufferedReader.readLine());
            sets.add(i, new LinkedList<>());
            for (int j = 0; j < nrCards; j++) {
                String card = bufferedReader.readLine();
                Integer index = neededCardsIndex.get(card);
                if (index != null) {
                    sets.get(i).add(index);
                    containingSets.get(index - 1).add(i + 1);
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
        System.out.println(selectedSets.size());
        for (final int selectedSet : selectedSets) {
            System.out.printf("%d ", selectedSet);
        }
        System.out.println();
    }

    public void solve() throws IOException {
        readProblemData();

        while (true) {
            ArrayList<Integer> setIndexes = containingSets.stream().max(Comparator.comparing(ArrayList::size)).get();

            if (setIndexes.size() == 0)
                break;

            LinkedList<Integer> largestList = sets.get(setIndexes.get(0));
            for (Integer setIndex : setIndexes) {
                if (largestList.size() < sets.get(setIndex - 1).size()) {
                    largestList = sets.get(setIndex - 1);
                }
            }

            int index = sets.indexOf(largestList);

            if (largestList.size() == 0)
                break;

            for (Integer val : largestList) {
                containingSets.get(val - 1).clear();
            }

            for (LinkedList<Integer> set : sets) {
                if (!set.equals(largestList)) {
                    set.removeAll(largestList);
                }
            }
            largestList.clear();

            selectedSets.add(index + 1);
        }

        writeAnswer();
    }

    public static void main(String[] args) throws IOException {
        new Redemption().solve();
    }
}
