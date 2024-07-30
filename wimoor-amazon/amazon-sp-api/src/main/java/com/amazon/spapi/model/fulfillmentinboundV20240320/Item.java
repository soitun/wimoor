/*
 * The Selling Partner API for FBA inbound operations.
 * The Selling Partner API for Fulfillment By Amazon (FBA) Inbound. The FBA Inbound API enables building inbound workflows to create, manage, and send shipments into Amazon's fulfillment network. The API has interoperability with the Send-to-Amazon user interface.
 *
 * OpenAPI spec version: 2024-03-20
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.amazon.spapi.model.fulfillmentinboundV20240320;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Information associated with a single SKU in the seller&#39;s catalog.
 */
@ApiModel(description = "Information associated with a single SKU in the seller's catalog.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2024-06-18T14:09:15.791+08:00")
public class Item {
  @SerializedName("asin")
  private String asin = null;

  @SerializedName("expiration")
  private String expiration = null;

  @SerializedName("fnsku")
  private String fnsku = null;

  @SerializedName("labelOwner")
  private String labelOwner = null;

  @SerializedName("manufacturingLotCode")
  private String manufacturingLotCode = null;

  @SerializedName("msku")
  private String msku = null;

  @SerializedName("prepInstructions")
  private List<PrepInstruction> prepInstructions = new ArrayList<PrepInstruction>();

  @SerializedName("quantity")
  private Integer quantity = null;

  public Item asin(String asin) {
    this.asin = asin;
    return this;
  }

   /**
   * The Amazon Standard Identification Number (ASIN) of the item.
   * @return asin
  **/
  @ApiModelProperty(required = true, value = "The Amazon Standard Identification Number (ASIN) of the item.")
  public String getAsin() {
    return asin;
  }

  public void setAsin(String asin) {
    this.asin = asin;
  }

  public Item expiration(String expiration) {
    this.expiration = expiration;
    return this;
  }

   /**
   * The expiration date of the MSKU in ISO 8601 format. The same MSKU with different expiration dates cannot go into the same box.
   * @return expiration
  **/
  @ApiModelProperty(value = "The expiration date of the MSKU in ISO 8601 format. The same MSKU with different expiration dates cannot go into the same box.")
  public String getExpiration() {
    return expiration;
  }

  public void setExpiration(String expiration) {
    this.expiration = expiration;
  }

  public Item fnsku(String fnsku) {
    this.fnsku = fnsku;
    return this;
  }

   /**
   * A unique identifier assigned by Amazon to products stored in and fulfilled from an Amazon fulfillment center.
   * @return fnsku
  **/
  @ApiModelProperty(required = true, value = "A unique identifier assigned by Amazon to products stored in and fulfilled from an Amazon fulfillment center.")
  public String getFnsku() {
    return fnsku;
  }

  public void setFnsku(String fnsku) {
    this.fnsku = fnsku;
  }

  public Item labelOwner(String labelOwner) {
    this.labelOwner = labelOwner;
    return this;
  }

   /**
   * Specifies who will label the items. Options include &#x60;AMAZON&#x60;, &#x60;SELLER&#x60;, and &#x60;NONE&#x60;.
   * @return labelOwner
  **/
  @ApiModelProperty(required = true, value = "Specifies who will label the items. Options include `AMAZON`, `SELLER`, and `NONE`.")
  public String getLabelOwner() {
    return labelOwner;
  }

  public void setLabelOwner(String labelOwner) {
    this.labelOwner = labelOwner;
  }

  public Item manufacturingLotCode(String manufacturingLotCode) {
    this.manufacturingLotCode = manufacturingLotCode;
    return this;
  }

   /**
   * The manufacturing lot code.
   * @return manufacturingLotCode
  **/
  @ApiModelProperty(value = "The manufacturing lot code.")
  public String getManufacturingLotCode() {
    return manufacturingLotCode;
  }

  public void setManufacturingLotCode(String manufacturingLotCode) {
    this.manufacturingLotCode = manufacturingLotCode;
  }

  public Item msku(String msku) {
    this.msku = msku;
    return this;
  }

   /**
   * The merchant defined SKU ID.
   * @return msku
  **/
  @ApiModelProperty(required = true, value = "The merchant defined SKU ID.")
  public String getMsku() {
    return msku;
  }

  public void setMsku(String msku) {
    this.msku = msku;
  }

  public Item prepInstructions(List<PrepInstruction> prepInstructions) {
    this.prepInstructions = prepInstructions;
    return this;
  }

  public Item addPrepInstructionsItem(PrepInstruction prepInstructionsItem) {
    this.prepInstructions.add(prepInstructionsItem);
    return this;
  }

   /**
   * Special preparations that are required for an item.
   * @return prepInstructions
  **/
  @ApiModelProperty(required = true, value = "Special preparations that are required for an item.")
  public List<PrepInstruction> getPrepInstructions() {
    return prepInstructions;
  }

  public void setPrepInstructions(List<PrepInstruction> prepInstructions) {
    this.prepInstructions = prepInstructions;
  }

  public Item quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

   /**
   * The number of the specified MSKU.
   * minimum: 1
   * maximum: 10000
   * @return quantity
  **/
  @ApiModelProperty(required = true, value = "The number of the specified MSKU.")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Item item = (Item) o;
    return Objects.equals(this.asin, item.asin) &&
        Objects.equals(this.expiration, item.expiration) &&
        Objects.equals(this.fnsku, item.fnsku) &&
        Objects.equals(this.labelOwner, item.labelOwner) &&
        Objects.equals(this.manufacturingLotCode, item.manufacturingLotCode) &&
        Objects.equals(this.msku, item.msku) &&
        Objects.equals(this.prepInstructions, item.prepInstructions) &&
        Objects.equals(this.quantity, item.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(asin, expiration, fnsku, labelOwner, manufacturingLotCode, msku, prepInstructions, quantity);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Item {\n");
    
    sb.append("    asin: ").append(toIndentedString(asin)).append("\n");
    sb.append("    expiration: ").append(toIndentedString(expiration)).append("\n");
    sb.append("    fnsku: ").append(toIndentedString(fnsku)).append("\n");
    sb.append("    labelOwner: ").append(toIndentedString(labelOwner)).append("\n");
    sb.append("    manufacturingLotCode: ").append(toIndentedString(manufacturingLotCode)).append("\n");
    sb.append("    msku: ").append(toIndentedString(msku)).append("\n");
    sb.append("    prepInstructions: ").append(toIndentedString(prepInstructions)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
