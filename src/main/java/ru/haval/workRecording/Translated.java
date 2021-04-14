package ru.haval.workRecording;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;

public class Translated {
  public Translated(String lang) {
    this.update(lang.substring(0, 2).toLowerCase());
  }

  public Translated() {
  }

  public void update(String lang) {
    ResourceBundle bundle = ResourceBundle.getBundle("bundles.LangBundle", new Locale(lang));
    Field[] fields = this.getClass().getDeclaredFields();
    for (Field i : fields) {
      i.setAccessible(true);
      try {
        if (bundle.containsKey(i.getName())) {
          i.set(this, bundle.getString(i.getName()));
        } else {
          i.set(this, i.getName());
        }
      } catch (IllegalArgumentException | IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      i.setAccessible(false);
    }
  }
}
