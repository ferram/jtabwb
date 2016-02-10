/*******************************************************************************
 * Copyright (C) 2013, 2014 Mauro Ferrari
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
package jtabwb.engine;

/**
 * Utility class to manage the name of a prover. The prover name is
 * characterised by the prover proper noun (a single world), the version of the
 * prover, the variant of the prover (which can be determined at run-time on the
 * base of the specific configuration of the prover) and a verbose description
 * of the prover. Only the proper noun is mandatory, the other details are
 * optional.
 * 
 * 
 * @author Mauro Ferrari
 * 
 */
public class ProverName implements Comparable<ProverName> {

  /**
   * Constructs a prover version object only specifying the prover name;
   * <code>prover_name</code> cannot be null.
   * 
   * @param properNoun the name of the prover
   */
  public ProverName(String properNoun) {
    this(properNoun, null, null, null);
  }

  /**
   * Constructs a prover version object with the specified properties;
   * <code>prover_name</code> cannot be null. <code>version</code> can be null.
   * 
   * 
   * @param properNoun the name of the prover
   * @param version the prover version number
   */
  public ProverName(String properNoun, String version) {
    this(properNoun, version, null, null);
  }

  /**
   * Constructs a prover version object with the specified properties;
   * <code>prover_name</code> cannot be null. <code>version</code> and
   * <code>variant</code> can be null.
   * 
   * 
   * @param properNoun the name of the prover
   * @param version the prover version number
   * @param variant a string describing the variant of the prover
   */
  public ProverName(String properNoun, String version, String variant) {
    this(properNoun, version, variant, null);
  }

  /**
   * Constructs a prover version object with the specified properties;
   * <code>prover_name</code> cannot be null. <code>version</code> and
   * <code>variant</code> can be null.
   * 
   * 
   * @param properNoun the name of the prover
   * @param version the prover version number
   * @param variant a string describing the variant of the prover
   * @param description a descriptionof the prover
   */
  public ProverName(String properNoun, String version, String variant, String description) {
    super();
    if (properNoun == null)
      throw new ImplementationError(MSG.IMPLEMENTATION_ERROR.PROPER_NOUN_CANNOT_BE_EMPTY);
    this.properNoun = properNoun;
    this.version = version;
    this.variant = variant;
    this.description = description;
  }

  private String properNoun = null;
  private String version = null;
  private String variant = null;
  private String description = null;

  /**
   * @return the properNoun
   */
  public String getProperNoun() {
    return this.properNoun;
  }

  /**
   * @return the version
   */
  public String getVersion() {
    return this.version;
  }

  /**
   * @return the variant
   */
  public String getVariant() {
    return this.variant;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Returns the string done as follows:
   * 
   * <pre>
   *   proper_noun version (variant)
   * </pre>
   * 
   * <code>version</code> and <code>(variant)</code> are present only if have
   * been specified.
   * 
   * @return the string describing the prover version
   */
  public String getDetailedName() {
    return properNoun + (version == null ? "" : " " + version)
        + (variant != null ? " (" + variant + ")" : "");
  }

  /**
   * @param properNoun the properNoun to set
   */
  public void setProperNoun(String properNoun) {
    if (properNoun == null)
      throw new ImplementationError(MSG.getMsg(MSG.IMPLEMENTATION_ERROR.ARGUMENT_CANNOT_BE_NULL_$1,
          "properNoun"));
    this.properNoun = properNoun;
  }

  /**
   * @param version the version to set
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * @param variant the variant to set
   */
  public void setVariant(String variant) {
    this.variant = variant;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(ProverName o) {
    int cname = this.properNoun.compareTo(o.properNoun);
    if (cname != 0)
      return cname;

    int cversion = this.version == null ? (o.version == null ? 0 : -1) : (o.version == null ? 1
        : this.version.compareTo(o.version));

    if (cversion != 0)
      return cversion;

    int cvariant = this.variant == null ? (o.variant == null ? 0 : -1) : (o.variant == null ? 1
        : this.variant.compareTo(o.variant));

    return cvariant;
  }

  /*
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.properNoun == null) ? 0 : this.properNoun.hashCode());
    result = prime * result + ((this.variant == null) ? 0 : this.variant.hashCode());
    result = prime * result + ((this.version == null) ? 0 : this.version.hashCode());
    return result;
  }

  /**
   * Two objects of this class are equal if their proper noun, version and
   * variant.
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProverName other = (ProverName) obj;
    if (this.properNoun == null) {
      if (other.properNoun != null)
        return false;
    } else if (!this.properNoun.equals(other.properNoun))
      return false;
    if (this.variant == null) {
      if (other.variant != null)
        return false;
    } else if (!this.variant.equals(other.variant))
      return false;
    if (this.version == null) {
      if (other.version != null)
        return false;
    } else if (!this.version.equals(other.version))
      return false;
    return true;
  }

  /*
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ProverName [properNoun=" + this.properNoun + ", version=" + this.version + ", variant="
        + this.variant + ", description=" + this.description + "]";
  }

}
