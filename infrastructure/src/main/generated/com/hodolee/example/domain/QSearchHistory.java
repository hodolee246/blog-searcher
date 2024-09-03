package com.hodolee.example.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSearchHistory is a Querydsl query type for SearchHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSearchHistory extends EntityPathBase<SearchHistory> {

    private static final long serialVersionUID = -1900518201L;

    public static final QSearchHistory searchHistory = new QSearchHistory("searchHistory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath keyword = createString("keyword");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastSearchTime = _super.lastSearchTime;

    public QSearchHistory(String variable) {
        super(SearchHistory.class, forVariable(variable));
    }

    public QSearchHistory(Path<? extends SearchHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSearchHistory(PathMetadata metadata) {
        super(SearchHistory.class, metadata);
    }

}

