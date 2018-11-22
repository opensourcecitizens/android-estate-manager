package io.mtini.model;

import java.sql.SQLException;
import java.util.List;

public interface AppDAOInterface {

    enum EstateTblDescription{

        KEY_ID("id","TEXT"),
        KEY_NAME("name","TEXT"),
        ADDRESS("address","TEXT"),
        CONTACTS("contacts","TEXT"),
        DESCRIPTION("description","TEXT"),
        TYPE("type","TEXT");

        String label;
        String type;
        EstateTblDescription(String label, String type){
            this.label = label;
            this.type = type;
        }

    }

    enum TenantTblDescription{

        KEY_ID("id","TEXT"),
        KEY_ESTATE_ID("estateid","TEXT"),
        KEY_NAME("name","TEXT"),
        DESCRIPTION("description","TEXT"),
        BLDG_NUMBER("number","TEXT"),
        RENT_DUE("rentduedate","DATE"),
        STATUS("status","TEXT"),
        RENT("rent","INTEGER"),
        BALANCE("balance","INTEGER"),
        CONTACTS("contacts","TEXT");

        String label;
        String type;
        TenantTblDescription(String label, String type){
            this.label = label;
            this.type = type;
        }

    }

    public AppDAO open()throws SQLException;

    public EstateModel getEstateById(String id);

    public TenantModel getTenantById(String id);

    public List<EstateModel> getMyEstateList();

    public List<TenantModel> getTenantList(EstateModel propertyInfo);

    public EstateModel addEstate(EstateModel newProperty);

    public TenantModel addTenant(TenantModel newtenant,EstateModel property);

    public boolean deleteEstate(EstateModel Property);

    public boolean deleteTenant(TenantModel tenant,EstateModel property);

    public EstateModel updateEstate(EstateModel Property);

    public TenantModel updateTenant(TenantModel tenant,EstateModel property);

    public TenantModel updateTenant(TenantModel tenant);

}
