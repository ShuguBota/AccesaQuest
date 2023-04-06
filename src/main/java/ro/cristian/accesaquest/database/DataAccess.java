package ro.cristian.accesaquest.database;

import com.azure.cosmos.*;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.FeedResponse;
import com.azure.cosmos.models.SqlQuerySpec;
import org.json.simple.JSONObject;
import ro.cristian.accesaquest.util.Config;

import java.util.ArrayList;
import java.util.List;

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
    private static final CosmosContainer quest_container = db.getContainer(Config.questsContainer);

    public static List<JSONObject> queryDBList(String container, SqlQuerySpec query){
        CosmosContainer queryContainer = getQueryContainer(container);
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();

        //Limit the amount
        int maxBufferedItemCount = 100;
        int maxDegreeOfParallelism = 1000;
        int maxItemCount = 1000;
        options.setMaxBufferedItemCount(maxBufferedItemCount);
        options.setMaxDegreeOfParallelism(maxDegreeOfParallelism);
        options.setQueryMetricsEnabled(false);

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

        if(itemList.size() > 0){
            return itemList;
        }
        else{
            return null;
        }
    }

    public static JSONObject queryDBObject(String container, SqlQuerySpec query) {
        var res = queryDBList(container, query);
        if(res == null) return null;
        return res.get(0);
    }

    public static CosmosContainer getQueryContainer(String container){
        CosmosContainer queryContainer = null;

        if(container.equals("players")) queryContainer = player_container;
        if(container.equals("quests")) queryContainer = quest_container;

        return queryContainer;
    }

    public static void createDBObject(String container, JSONObject json){
        CosmosContainer queryContainer = getQueryContainer(container);
        queryContainer.createItem(json);
    }
}
