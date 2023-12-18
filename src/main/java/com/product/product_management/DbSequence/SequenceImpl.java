package com.product.product_management.DbSequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SequenceImpl implements Sequence{
    @Autowired
    private MongoOperations mongoOperations;
    @Override
    public int getSequenceNumber(String seqName) {
        Query query =new Query(Criteria.where("id").is(seqName));
        Update update = new Update().inc("seq",1);
        DatabaseSequence counter=mongoOperations.findAndModify(query,
                update, FindAndModifyOptions.options().returnNew(true).upsert(true),DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq():1;
    }
}
