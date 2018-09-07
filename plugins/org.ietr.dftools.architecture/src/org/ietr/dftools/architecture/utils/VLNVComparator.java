package org.ietr.dftools.architecture.utils;

import java.util.Comparator;
import org.ietr.dftools.architecture.slam.attributes.VLNV;

/**
 *
 */
public class VLNVComparator implements Comparator<VLNV> {

  public static final boolean areSame(final VLNV v1, final VLNV v2) {
    return new VLNVComparator().compare(v1, v2) == 0;
  }

  public static final int compareVLNV(final VLNV v1, final VLNV v2) {
    return new VLNVComparator().compare(v1, v2);
  }

  private static final int compare(final String str1, final String str2) {
    final boolean is1null = str1 == null;
    final boolean is2null = str2 == null;

    if (!is1null) {
      return str1.compareTo(str2);
    } else if (!is2null) {
      final int compareTo = str2.compareTo(str1);
      return -compareTo;
    } else {
      return 0;
    }
  }

  @Override
  public int compare(final VLNV v1, final VLNV v2) {
    int compare = VLNVComparator.compare(v1.getVendor(), v2.getVendor());
    if (compare == 0) {
      compare = VLNVComparator.compare(v1.getVendor(), v2.getVendor());
      if (compare == 0) {
        compare = VLNVComparator.compare(v1.getLibrary(), v2.getLibrary());
        if (compare == 0) {
          compare = VLNVComparator.compare(v1.getName(), v2.getName());
          if (compare == 0) {
            compare = VLNVComparator.compare(v1.getVersion(), v2.getVersion());
          }
        }
      }
    }
    return compare;
  }
}
