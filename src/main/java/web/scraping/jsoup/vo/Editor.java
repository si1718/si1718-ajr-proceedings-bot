package web.scraping.jsoup.vo;

public class Editor {
	private String viewURL, name, uri;

	public Editor() {
		super();
	}

	public String getViewURL() {
		return viewURL;
	}

	public void setViewURL(String viewURL) {
		this.viewURL = viewURL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		result = prime * result + ((viewURL == null) ? 0 : viewURL.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Editor other = (Editor) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		if (viewURL == null) {
			if (other.viewURL != null)
				return false;
		} else if (!viewURL.equals(other.viewURL))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Editor [viewURL=" + viewURL + ", name=" + name + ", uri=" + uri + "]";
	}
	
	
}
