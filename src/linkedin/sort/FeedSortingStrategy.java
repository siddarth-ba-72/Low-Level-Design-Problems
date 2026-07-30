package linkedin.sort;

import linkedin.models.Post;

import java.util.List;

public interface FeedSortingStrategy {
    List<Post> sort(List<Post> posts);
}
