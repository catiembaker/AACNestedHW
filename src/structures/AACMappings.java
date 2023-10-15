package structures;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * AACMappings class represents the mappings for a set of Augmentative and Alterntive Communication
 * (AAC) images and their associated texts. It manages categories, images, and their mappings,
 * allowing for easy retrieval and manipulation of AAC data
 * 
 * @author Rene Urias Jr.
 * @version 1.0 of 15 October 2023
 */
public class AACMappings {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+
  private AACCategory defaultCategory;
  private AACCategory currentCategory;
  private AssociativeArray<String, AACCategory> categories;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+
  /**
   * Constructs an AACMappings object
   * 
   * @param filename
   */
  public AACMappings(String filename) {
    // Initialzie the AACMappings with data from the file (to be implemented)
    // For now, let's create a default category
    defaultCategory = new AACCategory("default");
    currentCategory = defaultCategory;
    categories = new AssociativeArray<>();
    categories.set("default", defaultCategory);
  } // AACMappings(String)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+
  /**
   * Adds a mapping to the current category (or the default category if that is the current
   * category)
   * 
   * @param imageLoc The location of the image
   * @param text The text associated with the image
   */
  public void add(String imageLoc, String text) {
    currentCategory.addItem(imageLoc, text);
  } // add(String, String)

  /**
   * Provides an array of all the images in the current category
   * 
   * @return an array of image locations
   */
  public String[] getImageLocs() {
    return currentCategory.getImages();
  } // getImageLocs()

  /**
   * Given the image location selected, determines the associated text with the image. If the image
   * provided is a category, it also updates the AAC's current category to be the category
   * associated with that image.
   * 
   * @param imageLoc The location where the image is stored
   * @return the text associated with the current image
   * @throws KeyNotFoundException
   */
  public String getText(String imageLoc) throws KeyNotFoundException {
    // Check if the image represents a category
    if (isCategory(imageLoc)) {
      // Update the current category to the category associated with the image
      currentCategory = categories.get(imageLoc);
      return "Category changed to: " + currentCategory.getCategory();
    } else {
      // Retrieve the text associated with the image from the current category
      try {
        String text = currentCategory.getText(imageLoc);
        return "Text for image " + imageLoc + ": " + text;
      } catch (KeyNotFoundException e) {
        return "Text not found for image " + imageLoc;
      }
    }
  }

  /**
   * Resets the current category of the AAC back to the default category
   */
  public void reset() {
    currentCategory = defaultCategory;
  } // reset()

  /**
   * Gets the current category
   * 
   * @return the current category or the empty string if on the default category
   */
  public String getCurrentCategory() {
    return currentCategory.getCategory();
  } // getCurrentCategory()

  /**
   * Determines if the image represents a category or text-to-speech.
   * 
   * @param imageLoc The location where the image is stored
   * @return true if the image represents a category, false if the image represents text-to-speech
   */
  public boolean isCategory(String imageLoc) {
    return currentCategory.hasImage(imageLoc);
  } // isCategory(String)

  /**
   * Writes the AAC mappings stored to a file. The file is formatted as the text location of the
   * category followed by the text name of the category and then one line per item in the category
   * that starts with > and then has the file name and text of that image. For instance:
   * img/food/plate.png food >img/food/icons8-french-fries-96.png french fries
   * >img/food/icons8-watermelon-96.png watermelon img/clothing/hanger.png clothing
   * >img/clothing/collaredshirt.png collared shirt
   *
   * @param filename The name of the file to write the AAC mapping to.
   */

  public void writeToFile(String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      // Iterate through categories
      for (String categoryName : categories.getAllKeys()) {
        AACCategory category = categories.get(categoryName);

        // Write category information to the file
        writer.write(categoryName + " " + category.getCategory());
        writer.newLine();

        // Iterate through images in the category
        for (String imageLoc : category.getImages()) {
          String text = category.getText(imageLoc);

          // Write image information to the file
          writer.write(">" + imageLoc + " " + text);
          writer.newLine();
        }
      }
    } catch (IOException | KeyNotFoundException e) {
      e.printStackTrace(); // Handle or log exceptions based on your application's needs
    }
  }
} // class AACMappings
