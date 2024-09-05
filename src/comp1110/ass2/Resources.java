package comp1110.ass2;

import comp1110.ass2.Position;
import comp1110.ass2.Resource;

import java.util.*;

/**
 * Created by Zeqi Gao on 18/5/2023
 * Modified by Zeqi Gao on 19/5/2023
 */
public class Resources {

    private final Map<Resource, List<Position>> resources;

    public Resources() {
        this.resources = new HashMap<>();
    }

    public List<Position> getPositions() {
        return resources.values().stream().flatMap(Collection::stream).toList();
    }

    public Resources(String[] resourceStr) {
        this.resources = new LinkedHashMap<>();
        List<Position> positions;
        int fast = 2;
        int slow = 1;
        while (slow < resourceStr.length) {
            Resource resource = Resource.fromChar(resourceStr[slow]);
            positions = new ArrayList<>();
            while(fast < resourceStr.length && resourceStr[fast].contains(",")) {
                positions.add(new Position(resourceStr[fast]));
                fast ++;
            }
            slow = fast;
            fast ++;
            this.resources.put(resource, positions);
        }
    }

    public List<Position> getResource(Resource resource) {
        return new ArrayList<>(resources.get(resource));
    }

    public void removeResource(String resourceStr) {
        Position position = new Position(resourceStr);
        for (Resource resource : resources.keySet()) {
            if (resources.get(resource).contains(position)) {
                resources.get(resource).remove(position);
                break;
            }
        }
    }

    public void addResource(Resource resource, List<Position> positions) {
        this.resources.put(resource, positions);
    }

    public void addResource(Resource resource, Position positions) {
        if (!resources.containsKey(resource)) {
            return;
        }
        resources.get(resource).add(positions);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("r").append(" ");
        Iterator<Map.Entry<Resource, List<Position>>> iterator = resources.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Resource, List<Position>> next = iterator.next();
            builder.append(next.getKey().getC()).append(" ");
            for (Position position : next.getValue()) {
                builder.append(position.toString()).append(" ");
            }
        }
        return builder.toString().trim();
    }

    public Resource getResourceByPosition(Position position) {
        for (Map.Entry<Resource, List<Position>> next : resources.entrySet()) {
            if (next.getValue().contains(position)) {
                return next.getKey();
            }
        }
        return null;
    }
}