/*
 * Selling Partner API for A+ Content Management
 * With the A+ Content API, you can build applications that help selling partners add rich marketing content to their Amazon product detail pages. A+ content helps selling partners share their brand and product story, which helps buyers make informed purchasing decisions. Selling partners assemble content by choosing from content modules and adding images and text.
 *
 * OpenAPI spec version: 2020-11-01
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.amazon.spapi.model.apluscontent;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A standard headline and body text with an image on the side.
 */
@ApiModel(description = "A standard headline and body text with an image on the side.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2024-01-30T16:09:20.221+08:00")
public class StandardSingleSideImageModule {
  @SerializedName("imagePositionType")
  private PositionType imagePositionType = null;

  @SerializedName("block")
  private StandardImageTextBlock block = null;

  public StandardSingleSideImageModule imagePositionType(PositionType imagePositionType) {
    this.imagePositionType = imagePositionType;
    return this;
  }

   /**
   * Get imagePositionType
   * @return imagePositionType
  **/
  @ApiModelProperty(required = true, value = "")
  public PositionType getImagePositionType() {
    return imagePositionType;
  }

  public void setImagePositionType(PositionType imagePositionType) {
    this.imagePositionType = imagePositionType;
  }

  public StandardSingleSideImageModule block(StandardImageTextBlock block) {
    this.block = block;
    return this;
  }

   /**
   * Get block
   * @return block
  **/
  @ApiModelProperty(value = "")
  public StandardImageTextBlock getBlock() {
    return block;
  }

  public void setBlock(StandardImageTextBlock block) {
    this.block = block;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StandardSingleSideImageModule standardSingleSideImageModule = (StandardSingleSideImageModule) o;
    return Objects.equals(this.imagePositionType, standardSingleSideImageModule.imagePositionType) &&
        Objects.equals(this.block, standardSingleSideImageModule.block);
  }

  @Override
  public int hashCode() {
    return Objects.hash(imagePositionType, block);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StandardSingleSideImageModule {\n");
    
    sb.append("    imagePositionType: ").append(toIndentedString(imagePositionType)).append("\n");
    sb.append("    block: ").append(toIndentedString(block)).append("\n");
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

