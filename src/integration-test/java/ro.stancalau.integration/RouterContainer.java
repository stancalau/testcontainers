package ro.stancalau.integration;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitStrategy;
import org.testcontainers.utility.MountableFile;

public class RouterContainer extends GenericContainer<RouterContainer> {

    public static final String SOURCE = "adoptopenjdk/openjdk11:armv7l-centos-jdk-11.0.6_10";

    private final Network network;
    private final String alias;

    private RouterContainer(String source, Network network, String alias) {
        super(source);
        this.network = network;
        this.alias = alias;
    }

    public static RouterContainer createContainer(Network network, String alias, int appPort) {

        RouterContainer container = new RouterContainer(SOURCE, network, alias)
                .withExposedPorts(appPort)
                .withEnv("HOP_NAME", alias)
                .withCopyFileToContainer(MountableFile.forHostPath("build/libs/TestContainers-1.0-SNAPSHOT.jar"),
                        "router.jar")
                .withCommand("java", "-jar", "router.jar")
                .waitingFor(Wait.forListeningPort())
                .withNetwork(network)
                .withNetworkAliases(alias);

        try {
            MountableFile application = MountableFile.forClasspathResource("deployments/" + alias + "/application.yml");
            container.withCopyFileToContainer(application,
                    "application.yml");
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("application.yml could not be found for container " + alias);
        }

        return container;
    }

}
