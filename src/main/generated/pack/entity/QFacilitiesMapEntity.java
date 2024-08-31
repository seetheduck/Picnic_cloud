package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFacilitiesMapEntity is a Querydsl query type for FacilitiesMapEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFacilitiesMapEntity extends EntityPathBase<FacilitiesMapEntity> {

    private static final long serialVersionUID = 1252047144L;

    public static final QFacilitiesMapEntity facilitiesMapEntity = new QFacilitiesMapEntity("facilitiesMapEntity");

    public final StringPath address = createString("address");

    public final StringPath ageLimit = createString("ageLimit");

    public final StringPath category = createString("category");

    public final BooleanPath chargedParking = createBoolean("chargedParking");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final BooleanPath freeParking = createBoolean("freeParking");

    public final StringPath holidayInfo = createString("holidayInfo");

    public final StringPath homepageUrl = createString("homepageUrl");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath instagramUrl = createString("instagramUrl");

    public final BooleanPath kidsZone = createBoolean("kidsZone");

    public final NumberPath<Float> latitude = createNumber("latitude", Float.class);

    public final NumberPath<Float> longitude = createNumber("longitude", Float.class);

    public final StringPath name = createString("name");

    public final BooleanPath nursingRoom = createBoolean("nursingRoom");

    public final StringPath operationTime = createString("operationTime");

    public final BooleanPath strollerRental = createBoolean("strollerRental");

    public final StringPath telephone = createString("telephone");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QFacilitiesMapEntity(String variable) {
        super(FacilitiesMapEntity.class, forVariable(variable));
    }

    public QFacilitiesMapEntity(Path<? extends FacilitiesMapEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFacilitiesMapEntity(PathMetadata metadata) {
        super(FacilitiesMapEntity.class, metadata);
    }

}

