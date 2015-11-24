package server.udp;

import util.PacketDetails;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Watcher implements Runnable
{
    private Map<InetAddress, PacketDetails> clients;

    /**
     *
     * @param clients a map containing the clients and their incomplete transmission
     */
    Watcher(Map<InetAddress, PacketDetails> clients) {
        this.clients = clients;
    }

    /**
     * The main thread which watch
     * the list of clients and remove the
     * outdated ones
     */
    public void run() {
        List<InetAddress> toRemove =
                clients.entrySet()
                        .stream()
                        .filter(client -> client.getValue().isOutdated())
                        .map(Map.Entry::getKey).collect(Collectors.toList());

        toRemove.stream().forEach(address -> clients.remove(address));
        toRemove.clear();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignore) {}
    }
}
