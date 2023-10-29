package com.fastcampus.fcprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false)
    private String title;
    @Setter @Column(nullable = false, length = 10000)
    private String content;
    @Setter
    private String hashtag;

    //리스트, 셋, 맵 여러 가지가 있다. 용도에 따라 다름.
    //이 article 에 연동되어 있는 comment는 중복을 허용하지 않고 다 여기에서 모아서 collection으로 보겠다
    //이런 의도를 원투맨으로 보여주고 있다.
    //mappedby 를 안해주면 article, articlecomment 를 합쳐서 테이블을 만든다.
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    @CreatedDate @Column(nullable = false)
    private LocalDateTime createdAt;
    @CreatedBy @Column(nullable = false, length = 100)
    private String createdBy;
    @LastModifiedDate @Column(nullable = false)
    private LocalDateTime modifiedAt;
    @LastModifiedBy @Column(nullable = false, length = 100)
    private String modifiedBy;



    //private 말고 protected, public 으로 기본 생성자 생성
    protected Article() {
    }
    //private 로 막고 Factory Method 를 통해서 이걸 제공할 수 있게 한다
    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }
    //사용이 편하게 New 키워드를 쓰지 않고 쓸 수 있게..
    //의도를 전달
    //도메인 아티클을 생성하고자 할 때, 어떤 값이 필요로 한다는 것을 이것으로 가이드 해주는 것
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    //리스트에 넣거나 혹은 리스트에 있는 내용에서 중복 요소를 제거하거나 컬렉션에서 중복 요소를 제거하거나
    //혹은 정렬을 해야 할 때 비교 같은 걸 할 수 있어야 하는데
    //해서 동일성, 동등성 검사를 할 수 있는 ..
    //롬복을 사용할 수는 있지만 @EqualsAndHashCode 로
    //그걸로 하게 되면 여기 있는 필드를 모두 다 비교해서 보통의 표준적인 방법으로 구현하는 것.

    //엔티티 만큼은 좀 독특한 방법으로 equals hashcode 를 만들 것임
    //id 만 비교해도 동일한지 비교가능 -> 그래서 롬복으로 사용하지 않는 것

    //엔티티를 데이터베이스에 영속화 시키고 연결짓고 사용하는 환경에서 서로 다른 두 로우가, 두 엔티티가
    //같은 조건이 무엇인가에 대한 질문에 지금 이 equals 가 답을 하고 있음
    //id가 부여되지 않았다, 즉 영속화되지 않았다고 하면 동등성 곰사 자체가 의미가 없는 걸로 보고 다 다른 것으로 간주하거나 혹은 처리하지 않겠다는 뜻
    //게시글과 댓글 관계, 서로 이제 원투맨이 매니투원 연결하는데 거기에서 빛을 발함
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
