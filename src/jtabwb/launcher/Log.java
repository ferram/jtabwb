/*******************************************************************************
 * Copyright (C) 2013, 2016 Mauro Ferrari
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package jtabwb.launcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Log {

  private static String ERROR = "ERROR -- ";
  private static String PROVER_INFO = "** ";

  void info(String key, Object... args) {
    System.out.println(String.format(key, args));
  }

  void infoNoLn(String key, Object... args) {
    System.out.print(String.format(key, args));
  }

  void prover_info(String key, Object... args) {
    System.out.println(PROVER_INFO + String.format(key, args));
  }

  void error(String key, Object... args) {
    System.out.println(ERROR + String.format(key, args));
  }

  /**
   * Write on <code>file</code> the given message; if the file exists its
   * content is overwritten.
   * 
   * @param file the output file.
   * @param msg the message to write.
   */
  public void writeToFile(File file, String msg) {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
      bw.write(msg);
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  /**
   * Write the specified message at the end of <code>file</code>.
   * 
   * @param file the output file.
   * @param msg the message to write.
   */
  public void addToFile(File file, String msg) {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
      bw.write(msg);
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

}
