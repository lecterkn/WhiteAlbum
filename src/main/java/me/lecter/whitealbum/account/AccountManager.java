package me.lecter.whitealbum.account;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class AccountManager {
	
	private static final String FILE_NAME = "saved";
	private static List<SavedAccount> savedAccount = new ArrayList<SavedAccount>();
	private static final Type FILE_TYPE = (new TypeToken<List<SavedAccount>>() {}).getType();

	public static boolean load() {
		System.out.println("loading saved accounts...");

		InputStreamReader isr;
		try {
			isr = new InputStreamReader(new FileInputStream(FILE_NAME));
		} catch (FileNotFoundException e) {
			System.out.println("file not found: \"" + FILE_NAME + "\"");
			return false;
		}

		JsonReader reader = new JsonReader(isr);
		savedAccount = new Gson().fromJson(reader, FILE_TYPE);
		if (savedAccount == null) {
			System.out.println("failed to load saved accounts");
			savedAccount = new ArrayList<SavedAccount>();
		}

		System.out.println("loaded accounts");
		for (SavedAccount account : savedAccount) {
			System.out.println(account.getUsername());
		}

		return true;
	}

	public static boolean save() {
		for (SavedAccount account : savedAccount) {
			System.out.println(account.getUsername());
		}

		try (FileWriter writer = new FileWriter(FILE_NAME)) {
			new Gson().toJson(savedAccount, writer);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean save(String username, String access_token, String entitleentitlements_token) {
		return save(SavedAccount.create(username, access_token, entitleentitlements_token));
	}

	public static boolean save(SavedAccount account) {
		add(account);
		return save();
	}

	public static void add(SavedAccount account) {
		for (SavedAccount acc : savedAccount) {
			if (acc.getUsername().equals(account.getUsername())) {
				acc.setAccess_token(account.getAccess_token());
				acc.setEntitlements_token(account.getEntitlements_token());
				return;
			}
		}
		savedAccount.add(account);
	}

	public static void add(String username, String access_token, String entitleentitlements_token) {
		add(SavedAccount.create(username, access_token, entitleentitlements_token));
	}

	public static List<SavedAccount> getSavedAccount() {
		return savedAccount;
	}
}
