package io.github.dug22.cipherlabs.resources;

public enum Resource {

    ABOUT_CIPHER_LABS("About Cipher Labs", "/resource-material/about-cipher-labs-article.html", ResourceType.DOCUMENTATION),
    ABOUT_CIPHER_LABS_RESOURCES("About Cipher Labs Resources", "/resource-material/about-cipher-labs-resources-article.html",  ResourceType.DOCUMENTATION),
    ABOUT_LOCK_SPOT("About Lock Spot", "/resource-material/about-lock-spot-article.html",  ResourceType.DOCUMENTATION),
    CHANGE_LOG("Cipher Labs Changelog", "/resource-material/changelog.html",  ResourceType.DOCUMENTATION),
    AFFINE_CIPHER_RESOURCE("Affine Cipher", "/resource-material/affine-cipher-article.html", ResourceType.CIPHER),
    ATBASH_CIPHER_RESOURCE("Atbash Cipher", "/resource-material/atbash-cipher-article.html",  ResourceType.CIPHER),
    BACONIAN_CIPHER_RESOURCE("Baconian Cipher", "/resource-material/baconian-cipher-article.html",  ResourceType.CIPHER),
    CAESAR_CIPHER_RESOURCE("Caesar Cipher", "/resource-material/caesar-cipher-article.html",  ResourceType.CIPHER),
    ENIGMA_MACHINE_RESOURCE("Enigma Machine", "/resource-material/enigma-machine-article.html",  ResourceType.CIPHER),
    PLAYFAIR_CIPHER_RESOURCE("Playfair Cipher", "/resource-material/playfair-cipher-article.html",  ResourceType.CIPHER),
    POLYBIUS_CIPHER_RESOURCE("Polybius Cipher", "/resource-material/polybius-cipher-article.html",  ResourceType.CIPHER),
    PORTA_CIPHER_RESOURCE("Porta Cipher", "/resource-material/porta-cipher-article.html",  ResourceType.CIPHER),
    RAIL_FENCE_CIPHER_RESOURCE("Rail Fence Cipher", "/resource-material/rail-fence-cipher-article.html",  ResourceType.CIPHER),
    TEXTBOOK_RSA_RESOURCE("Textbook RSA", "/resource-material/textbook-rsa-article.html",  ResourceType.CIPHER),
    VIGENERE_CIPHER_RESOURCE("Vigenère Cipher", "/resource-material/vigenère-cipher-article.html",  ResourceType.CIPHER)
    ;
    private final String resourceName;
    private final String resourceHtmlArticle;
    private final ResourceType resourceType;

    Resource(String resourceName, String resourceHtmlArticle, ResourceType resourceType) {
        this.resourceName = resourceName;
        this.resourceHtmlArticle = resourceHtmlArticle;
        this.resourceType = resourceType;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceHtmlArticle() {
        return resourceHtmlArticle;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
