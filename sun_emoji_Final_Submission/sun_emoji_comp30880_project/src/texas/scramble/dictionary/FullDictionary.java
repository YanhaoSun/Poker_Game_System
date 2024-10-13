package texas.scramble.dictionary;

// Singleton Dictionary that contains every valid word

public class FullDictionary extends DictionaryTrie {
    private static final String pathToFullDictionary = "resources/full_dict.txt";

    private static FullDictionary cache = null;

    // Constructor

    private FullDictionary() {
        super(pathToFullDictionary);
    }

    public static synchronized FullDictionary getInstance() {
        if (cache == null) {
            cache = new FullDictionary();
        }
        return cache;
    }
}
