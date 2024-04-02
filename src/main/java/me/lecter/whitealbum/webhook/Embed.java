package me.lecter.whitealbum.webhook;

public class Embed {
	private String title;
	private String description;
	private int color;
	private Image image;
	public static final int RED = 0xFF0000;
	public static final int GREEN = 0x00FF00;
	public static final int BLUE = 0x0000FF;

	private Embed(String title, String description, int color, Embed.Image image) {
		this.title = title;
		this.description = description;
		this.color = color;
		this.image = image;
	}

	public static Embed create(String title, String description, int color, Embed.Image image) {
		return new Embed(title, description, color, image);
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getColor() {
		return this.color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Embed.Image getImage() {
		return this.image;
	}

	public void setImage(Embed.Image image) {
		this.image = image;
	}

	public static class Image {
		private String url;

		private Image(String url) {
			this.url = url;
		}

		public static Embed.Image create(String url) {
			return new Embed.Image(url);
		}

		public String getUrl() {
			return this.url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
}
