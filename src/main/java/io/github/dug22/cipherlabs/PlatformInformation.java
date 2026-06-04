package io.github.dug22.cipherlabs;

public interface PlatformInformation {

    default String name(){
        return "Cipher Labs";
    }

    default String description(){
        return "An educational platform to learn about cryptography";
    }

    default String version(){
        return "Version: 1.0.0";
    }

    default String repositoryInformation(){
        return "Repo: https://github.com/dug22/Cipher-Labs";
    }
}
