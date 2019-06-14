package by.itechart.server.entity;

import by.itechart.server.dto.ConfirmationTokenDto;
import by.itechart.server.transformers.ToDtoTransformer;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken implements ToDtoTransformer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //todo: why name equals name of class
    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date createDate;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public ConfirmationToken() {
    }

    public ConfirmationToken(User user){
        this.user = user;
        createDate = new Date();
        confirmationToken = String.valueOf(Math.random());
    }

    @Override
    public ConfirmationTokenDto transformToDto() {
        return ConfirmationTokenDto.builder()
                .withConfirmationToken(this.confirmationToken)
                .withCreateDate(this.createDate)
//                .withId(this.id)
                .withUser(this.user.transformToDto())
                .build();
    }
}
