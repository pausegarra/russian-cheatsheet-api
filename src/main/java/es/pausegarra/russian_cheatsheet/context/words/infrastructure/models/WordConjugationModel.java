package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.common.infrastructure.audit.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "word_conjugations")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@SuperBuilder
public class WordConjugationModel extends AuditableModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

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

  @OneToOne
  @JoinColumn(name = "word_id")
  private final WordModel word;

}
