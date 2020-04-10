package bulbul.boff.general.beans;

public class TreeItem  {
  private String pkColumn;
  private String nameColumn;
  private String descriptionColumn;
  private String fkColumn;
  private String tableName;

  public String getPkColumn() {
    return pkColumn;
  }

  public void setPkColumn(String newPkColumn) {
    pkColumn = newPkColumn;
  }

  public String getNameColumn() {
    return nameColumn;
  }

  public void setNameColumn(String newNameColumn) {
    nameColumn = newNameColumn;
  }

  public String getDescriptionColumn() {
    return descriptionColumn;
  }

  public void setDescriptionColumn(String newDescriptionColumn) {
    descriptionColumn = newDescriptionColumn;
  }

  public String getFkColumn() {
    return fkColumn;
  }

  public void setFkColumn(String newFkColumn) {
    fkColumn = newFkColumn;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String newTableName) {
    tableName = newTableName;
  }

}