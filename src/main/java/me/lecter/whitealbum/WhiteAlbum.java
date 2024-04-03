package me.lecter.whitealbum;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import me.lecter.whitealbum.account.AccountManager;
import me.lecter.whitealbum.account.RiotAccount;
import me.lecter.whitealbum.client.RiotClient;
import me.lecter.whitealbum.client.ValorantAPI;
import me.lecter.whitealbum.client.exceptions.RiotException;
import me.lecter.whitealbum.client.valorant.ItemOffer;
import me.lecter.whitealbum.client.valorant.StoreFront;
import me.lecter.whitealbum.server.HttpServer;
import me.lecter.whitealbum.util.BrowserUtils;
import me.lecter.whitealbum.webhook.Embed;
import me.lecter.whitealbum.webhook.Webhook;

public class WhiteAlbum {

	private static Configs configs;
	private static List<RiotAccount> accounts;
	public static final String NAME = "WhiteAlbum";
	public static final String VERSION = "1.0";
	public static final String[] DEVELOPERS = {"x/twitter@iameugeosword", "github@lecterkn"};

	public static void main(String[] args) {
		System.out.println("=== INFOMATION ===");
		System.out.println(NAME + " " + VERSION);
		System.out.println("==================");
		System.out.println("=== DEVELOPERS ===");

		for (String developer : DEVELOPERS) {
			System.out.println(developer);
		}

		System.out.println("==================");
		loadConfigs();
		loadAccounts();

		if (!AccountManager.load()) {
			System.out.println("failed to load \"saved\"");
		}

		SpringApplication.run(HttpServer.class, args);
		BrowserUtils.open("http://localhost:8100/login");

		if (configs.isAutocheck_accounts()) {
			for (RiotAccount account : accounts) {
				RiotClient client = RiotClient.create(account.getUsername(), account.getPassword());
				try {
					if (client.login()) {
						ValorantAPI api = ValorantAPI.create(client.getAccess_token(), client.getEntitlements_token());
						StoreFront storefront = api.getStoreFront();
						Webhook webhook = Webhook.create(storefront.getUsername(), getConfigs().getAvatar_url());
						for (ItemOffer offer : storefront.getSingleItemOffers()) {
							webhook.getEmbeds().add(Embed.create(offer.getDisplayName(), String.valueOf(offer.getCost()), 65280, Embed.Image.create(offer.getDisplayIcon())));
						}

						if (Webhook.send_embed(webhook)) {
							System.out.println("[Webhook] Success \"" + storefront.getUsername() + "\"");
						}
					}
				} catch (RiotException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private static void loadAccounts() {
		accounts = new ArrayList<RiotAccount>();

		InputStreamReader isr;
		try {
			isr = new InputStreamReader(new FileInputStream("accounts.txt"));
		} catch (FileNotFoundException var4) {
			var4.printStackTrace();
			return;
		}

		BufferedReader br = new BufferedReader(isr);

		String line;
		try {
			while ((line = br.readLine()) != null) {
				if (line.startsWith("//") || !line.contains(":")) {
					continue;
				}
				String[] userpass = line.split(":", 2);
				accounts.add(new RiotAccount(userpass[0], userpass[1]));
				System.out.println("loaded account \"" + userpass[0] + "\"");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void loadConfigs() {
		InputStreamReader isr;
		
		try {
			isr = new InputStreamReader(new FileInputStream("configs.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		JsonReader reader = new JsonReader(isr);
		configs = new Gson().fromJson(reader, Configs.class);
		if (configs.getWebhook_url() != null && !configs.getWebhook_url().startsWith("https://discord.com/api/webhooks/")) {
			configs.setWebhook_url(null);
		}
	}

	public static List<RiotAccount> getAccounts() {
		return accounts;
	}

	public static Configs getConfigs() {
		return configs;
	}
}
