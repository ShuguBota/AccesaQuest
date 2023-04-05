package ro.cristian.accesaquest.database;

import com.azure.cosmos.*;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.FeedResponse;
import org.json.simple.JSONObject;
import ro.cristian.accesaquest.models.Config;

import java.util.ArrayList;

public class DataAccess {
    private static final String HOST = Config.host;
    private static final String MASTER_KEY = Config.masterKey;

    private static CosmosClient client = new CosmosClientBuilder()
            .endpoint(HOST)
            .key(MASTER_KEY)
            .consistencyLevel(ConsistencyLevel.EVENTUAL)
            .buildClient();

    private static final CosmosDatabase db = client.getDatabase(Config.databaseID);
    private static final CosmosContainer player_container = db.getContainer(Config.playersContainer);

    public static CosmosClient getClient() {
        return client;
    }

    public static JSONObject queryDB(String container, String query){
        CosmosContainer queryContainer = null;
        int maxItemCount = 1000;
        int maxDegreeOfParallelism = 1000;
        int maxBufferedItemCount = 100;
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
        options.setMaxBufferedItemCount(maxBufferedItemCount);
        options.setMaxDegreeOfParallelism(maxDegreeOfParallelism);
        options.setQueryMetricsEnabled(false);

        if(container.equals("players")) queryContainer = player_container;

        if(queryContainer == null) return null;

        var itemList = new ArrayList<JSONObject>();
        String continuationToken = null;
        do {
            for (FeedResponse<JSONObject> pageResponse : queryContainer.queryItems(query, options, JSONObject.class).iterableByPage(continuationToken, maxItemCount)) {

                continuationToken = pageResponse.getContinuationToken();
                for (JSONObject item : pageResponse.getElements()) {
                    itemList.add(item);
                }
            }

        } while (continuationToken != null);

        if (itemList.size() > 0) {
            return itemList.get(0);
        } else {
            return null;
        }
    }
}
