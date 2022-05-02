package com.study.spring.domain;

import com.study.spring.domain.card.SkillCard;
import com.study.spring.dto.OAuth;
import lombok.*;

import java.util.*;

@Getter
@Setter
public class User extends OAuth {
    private	String uid;
    private	String email;
    private	float exp;
    private	int	cold;
    private	int	level;
    private	int	cardPiece;
    private	String displayName;
    private	int	normalItem;
    private	int	luxuryItem;
    private List<SkillCard> skillList;

}
