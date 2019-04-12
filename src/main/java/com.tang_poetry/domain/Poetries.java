package com.tang_poetry.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "poetries")
public class Poetries {
    /**
     * <pre>
     * 
     * 表字段：java.lang.Integer.id
     * </pre>
     * 
     */
    @Id
    private Integer id;

    /**
     * <pre>
     * 
     * 表字段：java.lang.Integer.poet_id
     * </pre>
     * 
     */
    @Column(name = "poet_id")
    private Integer poetId;

    /**
     * <pre>
     * 
     * 表字段：java.lang.String.title
     * </pre>
     * 
     */
    private String title;

    /**
     * <pre>
     * 
     * 表字段：java.util.Date.created_at
     * </pre>
     * 
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * <pre>
     * 
     * 表字段：java.util.Date.updated_at
     * </pre>
     * 
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * <pre>
     * 
     * 表字段：java.lang.String.content
     * </pre>
     * 
     */
    private String content;

}