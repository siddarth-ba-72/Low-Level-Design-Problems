## Clarifying Requirements
- The cache should be generic and support any type of key-value pair, as long as keys are hashable.
- For now, we can limit operations to `get` and `put`.
- If a key is not found then return `null` or `-1`.
- If a `put` operation ison an existing key, then update the value and need to be moved to the front as most recently used.
- This cache will be used in multithreaded environment, so it must be thread-safe.
- Both `get` and `put` operations must tun in `O(1)` time on average.

### Functional Requirements
- Support `get(key)` operation: returns the value if the key exists, otherwise returns `null` or `-1`
- Support `put(key, value)` operation: inserts a new key-value pair or updates the value of an existing key
- If the cache exceeds its capacity, it should automatically evict the least recently used item.
- Both `get` and `put` operations should update the recency of the accessed or inserted item.
- Keys and values should be `generic (e.g., <K, V>)`, provided the keys are hashable.

### Non-Functional Requirements
- **Time Complexity**: Both `get` and `put` operations must run in O(1) time on average.
- **Thread Safety**: The implementation must be thread-safe for use in concurrent environments.
- **Modularity**: The design should follow object-oriented principles with clean separation of responsibilities.
- **Memory Efficiency**: The internal data structures should be optimized for speed and space within the defined constraints.

