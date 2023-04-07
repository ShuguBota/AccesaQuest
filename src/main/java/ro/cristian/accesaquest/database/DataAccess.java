package ro.cristian.accesaquest.database;

import com.azure.cosmos.*;
import com.azure.cosmos.models.*;
import org.json.simple.JSONObject;
import ro.cristian.accesaquest.util.Config;

import java.util.ArrayList;
import java.util.List;

public class DataAccess {
    private static final String HOST = Config.host;
    private static final String MASTER_KEY = Config.masterKey;
    private final static CosmosClient client = new CosmosClientBuilder() //Connection to the db
            .endpoint(HOST)
            .key(MASTER_KEY)
            .consistencyLevel(ConsistencyLevel.EVENTUAL)
            .buildClient();
    private static final CosmosDatabase db = client.getDatabase(Config.databaseID); //db info
    private static final CosmosContainer players_container = db.getContainer(Config.playersContainer);
    private static final CosmosContainer quests_container = db.getContainer(Config.questsContainer);
    private static final CosmosContainer badges_container = db.getContainer(Config.badgesContainer);

    /**
     * Find the container given the String
     *
     * @param container String of the container
     * @return          CosmosContainer object
     */
    public static CosmosContainer getQueryContainer(String container){
        CosmosContainer queryContainer = null;

        if(container.equals("players")) queryContainer = players_container;
        if(container.equals("quests")) queryContainer = quests_container;
        if(container.equals("badges")) queryContainer = badges_container;

        return queryContainer;
    }

    /**
     * Find the list of objects in the db that satisfy the query
     *
     * @param container        String with the container's name
     * @param query            query that needs to the executed
     * @return                 List of JSONObject found after the query
     * @throws CosmosException something went wrong with the call
     */
    public static List<JSONObject> findListObjects(String container, SqlQuerySpec query) throws CosmosException {
        CosmosContainer queryContainer = getQueryContainer(container);
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();

        //Setting the options
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

    /**
     * Find one object that satisfies the query
     *
     * @param container        String with the container's name
     * @param query            query that needs to the executed
     * @return                 JSONObject found after the query
     * @throws CosmosException something went wrong with the call
     */
    public static JSONObject findObject(String container, SqlQuerySpec query) throws CosmosException {
        var res = findListObjects(container, query);
        if(res == null) return null;
        return res.get(0);
    }

    /**
     * Create an object in the database
     *
     * @param container String with the container's name
     * @param json      the JSON of the object to be created
     * @return          Status code of the call
     */
    public static int createObject(String container, JSONObject json){
        CosmosContainer queryContainer = getQueryContainer(container);
        return queryContainer.createItem(json).getStatusCode();
    }

    /**
     * Replace an object from the database
     *
     * @param container String with the container's name
     * @param json      the JSON of the object to be created
     * @return          Status code of the call
     */
    public static int replaceObject(String container, JSONObject json) {
        CosmosContainer queryContainer = getQueryContainer(container);
        return queryContainer.replaceItem(json, (String) json.get("id"), new PartitionKey(json.get("id")), new CosmosItemRequestOptions()).getStatusCode();
    }

    /**
     * Delete an object from the database
     *
     * @param container String with the container's name
     * @param json      the JSON of the object to be created
     * @return          Status code of the call
     */
    public static int deleteObject(String container, JSONObject json) throws CosmosException {
        CosmosContainer queryContainer = getQueryContainer(container);
        return queryContainer.deleteItem((String) json.get("id"), new PartitionKey(json.get("id")), new CosmosItemRequestOptions()).getStatusCode();
    }

    public static int deleteObject(String container, String id) throws CosmosException {
        JSONObject json = new JSONObject();
        json.put("id", id);
        return deleteObject(container, json);
    }
}
