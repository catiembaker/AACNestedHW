package structures;

/**
 * Represents the mappings for a single page of items that should be displayed;
 * 
 * @author Rene Urias Jr.
 * @version 1.0 of 15 October 2023
 */
public class AACCategory {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+
  private String name;
  private AssociativeArray<String, String> imageTextMapping;


  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+
  /**
   * Creates a new empty category with the given name.
   * 
   * @param name The name of the category
   */
  public AACCategory(String name) {
    this.name = name;
    this.imageTextMapping = new AssociativeArray<>();
  } // AACCategory(String)

  // +----------------+----------------------------------------------
  // | Public methods |
  // +----------------+
  /**
   * Adds the mapping of the imageLoc to the text to the category
   * 
   * @param imageLoc The location of the image to add
   * @param text The text that the image maps to
   */
  public void addItem(String imageLoc, String text) {
    imageTextMapping.set(imageLoc, text);
  } // addItem(String, String)

  /**
   * Returns the name of the category
   * 
   * @return the name of the category
   */
  public String getCategory() {
    return name;
  } // getCategory()

  /**
   * Returns the text associated with the given image location in this category
   * 
   * @param imageLoc The location of the image
   * @return the text associated with the image
   * @throws KeyNotFoundException when the image location is not found
   */
  public String getText(String imageLoc) throws KeyNotFoundException {
    return imageTextMapping.get(imageLoc);
  } // getText(String)

  /**
   * Determines if the provided image location if stored in the category
   * 
   * @param imageLoc The location of the category
   * @return true if it is in the category, false otherwise
   */
  public boolean hasImage(String imageLoc) {
    return imageTextMapping.hasKey(imageLoc);
  } // hasImage(String)

  /**
   * Returns an array of all the images in the category
   * 
   * @return the array of image locations
   */
  public String[] getImages() {
    String[] images = new String[imageTextMapping.size()];
    int index = 0;
    for (String key : imageTextMapping.getAllKeys()) {
      images[index++] = key;
    }
    return images;
  } // getImages()
} // class AACCategory
