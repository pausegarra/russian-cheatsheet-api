package es.pausegarra.russian_cheatsheet.vocabulary.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.With;

import java.util.UUID;

@Entity
@Table(name = "verb_conjugations")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class VerbConjugationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @With
  private final UUID id;

  @OneToOne(optional = false)
  @JoinColumn(
    name = "word_id",
    nullable = false,
    unique = true
  )
  @With
  private final WordEntity word;

  @Column(name = "imperfective_present_first_person_singular")
  private final String imperfectivePresentFirstPersonSingular;

  @Column(name = "imperfective_present_second_person_singular")
  private final String imperfectivePresentSecondPersonSingular;

  @Column(name = "imperfective_present_third_person_singular")
  private final String imperfectivePresentThirdPersonSingular;

  @Column(name = "imperfective_present_first_person_plural")
  private final String imperfectivePresentFirstPersonPlural;

  @Column(name = "imperfective_present_second_person_plural")
  private final String imperfectivePresentSecondPersonPlural;

  @Column(name = "imperfective_present_third_person_plural")
  private final String imperfectivePresentThirdPersonPlural;

  @Column(name = "imperfective_past_masculine")
  private final String imperfectivePastMasculine;

  @Column(name = "imperfective_past_feminine")
  private final String imperfectivePastFeminine;

  @Column(name = "imperfective_past_neuter")
  private final String imperfectivePastNeuter;

  @Column(name = "imperfective_past_plural")
  private final String imperfectivePastPlural;

  @Column(name = "imperfective_future_first_person_singular")
  private final String imperfectiveFutureFirstPersonSingular;

  @Column(name = "imperfective_future_second_person_singular")
  private final String imperfectiveFutureSecondPersonSingular;

  @Column(name = "imperfective_future_third_person_singular")
  private final String imperfectiveFutureThirdPersonSingular;

  @Column(name = "imperfective_future_first_person_plural")
  private final String imperfectiveFutureFirstPersonPlural;

  @Column(name = "imperfective_future_second_person_plural")
  private final String imperfectiveFutureSecondPersonPlural;

  @Column(name = "imperfective_future_third_person_plural")
  private final String imperfectiveFutureThirdPersonPlural;

  @Column(name = "perfective_present_first_person_singular")
  private final String perfectivePresentFirstPersonSingular;

  @Column(name = "perfective_present_second_person_singular")
  private final String perfectivePresentSecondPersonSingular;

  @Column(name = "perfective_present_third_person_singular")
  private final String perfectivePresentThirdPersonSingular;

  @Column(name = "perfective_present_first_person_plural")
  private final String perfectivePresentFirstPersonPlural;

  @Column(name = "perfective_present_second_person_plural")
  private final String perfectivePresentSecondPersonPlural;

  @Column(name = "perfective_present_third_person_plural")
  private final String perfectivePresentThirdPersonPlural;

  @Column(name = "perfective_past_masculine")
  private final String perfectivePastMasculine;

  @Column(name = "perfective_past_feminine")
  private final String perfectivePastFeminine;

  @Column(name = "perfective_past_neuter")
  private final String perfectivePastNeuter;

  @Column(name = "perfective_past_plural")
  private final String perfectivePastPlural;

  @Column(name = "perfective_future_first_person_singular")
  private final String perfectiveFutureFirstPersonSingular;

  @Column(name = "perfective_future_second_person_singular")
  private final String perfectiveFutureSecondPersonSingular;

  @Column(name = "perfective_future_third_person_singular")
  private final String perfectiveFutureThirdPersonSingular;

  @Column(name = "perfective_future_first_person_plural")
  private final String perfectiveFutureFirstPersonPlural;

  @Column(name = "perfective_future_second_person_plural")
  private final String perfectiveFutureSecondPersonPlural;

  @Column(name = "perfective_future_third_person_plural")
  private final String perfectiveFutureThirdPersonPlural;

  public static VerbConjugationEntity create(
    UUID id, WordEntity word, String imperfectivePresentFirstPersonSingular,
    String imperfectivePresentSecondPersonSingular, String imperfectivePresentThirdPersonSingular,
    String imperfectivePresentFirstPersonPlural, String imperfectivePresentSecondPersonPlural,
    String imperfectivePresentThirdPersonPlural, String imperfectivePastMasculine, String imperfectivePastFeminine,
    String imperfectivePastNeuter, String imperfectivePastPlural, String imperfectiveFutureFirstPersonSingular,
    String imperfectiveFutureSecondPersonSingular, String imperfectiveFutureThirdPersonSingular,
    String imperfectiveFutureFirstPersonPlural, String imperfectiveFutureSecondPersonPlural,
    String imperfectiveFutureThirdPersonPlural, String perfectivePresentFirstPersonSingular,
    String perfectivePresentSecondPersonSingular, String perfectivePresentThirdPersonSingular,
    String perfectivePresentFirstPersonPlural, String perfectivePresentSecondPersonPlural,
    String perfectivePresentThirdPersonPlural, String perfectivePastMasculine, String perfectivePastFeminine,
    String perfectivePastNeuter, String perfectivePastPlural, String perfectiveFutureFirstPersonSingular,
    String perfectiveFutureSecondPersonSingular, String perfectiveFutureThirdPersonSingular,
    String perfectiveFutureFirstPersonPlural, String perfectiveFutureSecondPersonPlural,
    String perfectiveFutureThirdPersonPlural
  ) {
    return new VerbConjugationEntity(
      id, word, imperfectivePresentFirstPersonSingular, imperfectivePresentSecondPersonSingular,
      imperfectivePresentThirdPersonSingular, imperfectivePresentFirstPersonPlural,
      imperfectivePresentSecondPersonPlural, imperfectivePresentThirdPersonPlural, imperfectivePastMasculine,
      imperfectivePastFeminine, imperfectivePastNeuter, imperfectivePastPlural, imperfectiveFutureFirstPersonSingular,
      imperfectiveFutureSecondPersonSingular, imperfectiveFutureThirdPersonSingular,
      imperfectiveFutureFirstPersonPlural, imperfectiveFutureSecondPersonPlural, imperfectiveFutureThirdPersonPlural,
      perfectivePresentFirstPersonSingular, perfectivePresentSecondPersonSingular, perfectivePresentThirdPersonSingular,
      perfectivePresentFirstPersonPlural, perfectivePresentSecondPersonPlural, perfectivePresentThirdPersonPlural,
      perfectivePastMasculine, perfectivePastFeminine, perfectivePastNeuter, perfectivePastPlural,
      perfectiveFutureFirstPersonSingular, perfectiveFutureSecondPersonSingular, perfectiveFutureThirdPersonSingular,
      perfectiveFutureFirstPersonPlural, perfectiveFutureSecondPersonPlural, perfectiveFutureThirdPersonPlural
    );
  }


}
