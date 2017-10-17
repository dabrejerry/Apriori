import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Apriori {

	List<List<String>> transactions;
	Map<String, Integer> items;
	Map<List<String>, Float> support = new HashMap<>();
	int transactionMatrix[][];

	public static void main(String[] args) {
		float minSupport = 0.15f;
		Apriori apriori = new Apriori();
		apriori.readFile();
		apriori.showItems();
		apriori.showTransactions();
		apriori.createMatrix();
		apriori.showMatrix();
		List<List<String>> itemSet = apriori.createInitialItemSet(apriori.items);
		apriori.calculateSupport(itemSet, minSupport);
		apriori.showItemsSetSupport(apriori.support, 1);
		itemSet = apriori.createItemSet2(itemSet,2);
		apriori.calculateSupport(itemSet, minSupport);
		apriori.showItemsSetSupport(apriori.support, 2);

	}

	public Map<List<String>, Float> calculateSupport(List<List<String>> itemSet, float minSupport) {

		for (int itemSetValueIndex = 0; itemSetValueIndex < itemSet.size(); itemSetValueIndex++) {
			List<String> itemSetValue = itemSet.get(itemSetValueIndex);

			float supportValue = 0;
			for (int transactionIndex = 0; transactionIndex < transactionMatrix.length; transactionIndex++) {
				int count = 0;
				for (int itemIndex = 0; itemIndex < itemSetValue.size(); itemIndex++) {
					String item = itemSetValue.get(itemIndex);
					int itemId = items.get(item);
					if (transactionMatrix[transactionIndex][itemId] == 1) {
						count++;
					}
				}
				if (count == itemSetValue.size()) {
					supportValue += 1;
				}
			}
			supportValue = supportValue / transactionMatrix.length;
			if (supportValue >= minSupport) {
				support.put(itemSetValue, supportValue);
			}
		}
		return support;
	}

	public List<List<String>> createInitialItemSet(Map<String, Integer> items) {
		List<List<String>> itemSet = new ArrayList<List<String>>();
		for (String item : items.keySet()) {
			List<String> itemSetValue = new ArrayList<String>();
			itemSetValue.add(item);
			itemSet.add(itemSetValue);
		}
		return itemSet;
	}

	public List<List<String>> createItemSet(List<List<String>> itemSet) {
		List<List<String>> tempItemSet = new ArrayList<List<String>>();
		List<String> tempItemSetValue;
		for (int i = 0; i < itemSet.size() - 1; i++) {

			for (int j = i + 1; j < itemSet.size(); j++) {
				tempItemSetValue = new ArrayList<String>();
				tempItemSetValue.add(itemSet.get(i).get(0));
				tempItemSetValue.add(itemSet.get(j).get(0));
				tempItemSet.add(tempItemSetValue);
			}
		}
		return tempItemSet;
	}

	public List<List<String>> createItemSet2(List<List<String>> itemSet,int pass) {
		List<List<String>> tempItemSet = new ArrayList<List<String>>();
		List<String> tempItemSetValue;
		for (int i = 0; i < itemSet.size() - 1; i++) {

			for (int j = i + 1; j < itemSet.size(); j++) {
				tempItemSetValue = new ArrayList<String>();
				tempItemSetValue.add(itemSet.get(i).get(0));
				tempItemSetValue.add(itemSet.get(j).get(0));
				tempItemSet.add(tempItemSetValue);
			}

		}

		return tempItemSet;
	}

	public void createMatrix() {
		transactionMatrix = new int[transactions.size()][items.size()];
		for (int i = 0; i < transactions.size(); i++) {
			for (String item : transactions.get(i)) {
				if (items.get(item) != null) {
					transactionMatrix[i][items.get(item)]++;
				}
			}
		}
	}

	public void readFile() {
		FileReader fileReader;
		BufferedReader bufferedReader;
		String itemFile = "E:\\NJIT\\Sem 3\\Data mining\\Midterm project\\items.txt";
		String line = null;
		items = new HashMap<String, Integer>();
		try {
			fileReader = new FileReader(itemFile);
			bufferedReader = new BufferedReader(fileReader);
			int i = 0;
			while ((line = bufferedReader.readLine()) != null) {
				items.put(line.trim(), i);
				i++;
			}
			bufferedReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String transactionFile = "E:\\NJIT\\Sem 3\\Data mining\\Midterm project\\database1.csv";
		line = null;
		transactions = new ArrayList<List<String>>();
		try {
			fileReader = new FileReader(transactionFile);
			bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				String items[] = line.trim().split(",");
				List<String> transaction = new ArrayList<String>();
				for (String item : items) {
					transaction.add(item.trim());
				}
				transactions.add(transaction);
			}
			bufferedReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void showTransactions() {
		System.out.println();
		for (List<String> transaction : transactions) {
			System.out.println(transaction);
		}
	}

	public void showItems() {
		System.out.println();
		for (String item : items.keySet()) {
			System.out.println(item);
		}
	}

	public void showItemsSetSupport(Map<List<String>, Float> support, int pass) {
		System.out.println();
		for (List<String> itemValue : support.keySet()) {
			if (itemValue.size() == pass) {
				System.out.println(itemValue + " " + support.get(itemValue));
			}
		}
	}

	public void showMatrix() {
		System.out.println();
		for (int[] transaction : transactionMatrix) {
			for (int item : transaction) {
				System.out.print(item + " ");
			}
			System.out.println();
		}
	}

}
