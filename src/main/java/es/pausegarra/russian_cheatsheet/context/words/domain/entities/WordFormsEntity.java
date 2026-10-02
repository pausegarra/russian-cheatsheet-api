package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;

import java.util.stream.Stream;

@RegisterForReflection
@Builder
public record WordFormsEntity(
  String ru_base,
  String ru_noun_sg_nom,
  String ru_noun_sg_gen,
  String ru_noun_sg_dat,
  String ru_noun_sg_acc,
  String ru_noun_sg_inst,
  String ru_noun_sg_prep,
  String ru_noun_pl_nom,
  String ru_noun_pl_gen,
  String ru_noun_pl_dat,
  String ru_noun_pl_acc,
  String ru_noun_pl_inst,
  String ru_noun_pl_prep,
  String ru_verb_imperative_sg,
  String ru_verb_imperative_pl,
  String ru_verb_past_m,
  String ru_verb_past_f,
  String ru_verb_past_n,
  String ru_verb_past_pl,
  String ru_verb_presfut_sg1,
  String ru_verb_presfut_sg2,
  String ru_verb_presfut_sg3,
  String ru_verb_presfut_pl1,
  String ru_verb_presfut_pl2,
  String ru_verb_presfut_pl3,
  String ru_verb_gerund_present,
  String ru_verb_gerund_past,
  String ru_verb_participle_active_present,
  String ru_verb_participle_active_past,
  String ru_verb_participle_passive_present,
  String ru_verb_participle_passive_past,
  String ru_adj_comparative,
  String ru_adj_superlative,
  String ru_adj_short_m,
  String ru_adj_short_f,
  String ru_adj_short_n,
  String ru_adj_short_pl,
  String ru_adj_m_nom,
  String ru_adj_m_gen,
  String ru_adj_m_dat,
  String ru_adj_m_acc,
  String ru_adj_m_inst,
  String ru_adj_m_prep,
  String ru_adj_f_nom,
  String ru_adj_f_gen,
  String ru_adj_f_dat,
  String ru_adj_f_acc,
  String ru_adj_f_inst,
  String ru_adj_f_prep,
  String ru_adj_n_nom,
  String ru_adj_n_gen,
  String ru_adj_n_dat,
  String ru_adj_n_acc,
  String ru_adj_n_inst,
  String ru_adj_n_prep,
  String ru_adj_pl_nom,
  String ru_adj_pl_gen,
  String ru_adj_pl_dat,
  String ru_adj_pl_acc,
  String ru_adj_pl_inst,
  String ru_adj_pl_prep
) {

  public boolean isEmpty() {
    return (ru_base == null || ru_base.isBlank()) && isCompatibleWith(WordType.OTHER);
  }

  public boolean isCompatibleWith(WordType type) {
    if (type == null) {
      return false;
    }

    boolean hasNounForms = anyNonBlank(
      ru_noun_sg_nom, ru_noun_sg_gen, ru_noun_sg_dat, ru_noun_sg_acc, ru_noun_sg_inst, ru_noun_sg_prep,
      ru_noun_pl_nom, ru_noun_pl_gen, ru_noun_pl_dat, ru_noun_pl_acc, ru_noun_pl_inst, ru_noun_pl_prep
    );
    boolean hasVerbForms = anyNonBlank(
      ru_verb_imperative_sg, ru_verb_imperative_pl, ru_verb_past_m, ru_verb_past_f, ru_verb_past_n, ru_verb_past_pl,
      ru_verb_presfut_sg1, ru_verb_presfut_sg2, ru_verb_presfut_sg3,
      ru_verb_presfut_pl1, ru_verb_presfut_pl2, ru_verb_presfut_pl3,
      ru_verb_gerund_present, ru_verb_gerund_past,
      ru_verb_participle_active_present, ru_verb_participle_active_past,
      ru_verb_participle_passive_present, ru_verb_participle_passive_past
    );
    boolean hasAdjectiveForms = anyNonBlank(
      ru_adj_comparative, ru_adj_superlative, ru_adj_short_m, ru_adj_short_f, ru_adj_short_n, ru_adj_short_pl,
      ru_adj_m_nom, ru_adj_m_gen, ru_adj_m_dat, ru_adj_m_acc, ru_adj_m_inst, ru_adj_m_prep,
      ru_adj_f_nom, ru_adj_f_gen, ru_adj_f_dat, ru_adj_f_acc, ru_adj_f_inst, ru_adj_f_prep,
      ru_adj_n_nom, ru_adj_n_gen, ru_adj_n_dat, ru_adj_n_acc, ru_adj_n_inst, ru_adj_n_prep,
      ru_adj_pl_nom, ru_adj_pl_gen, ru_adj_pl_dat, ru_adj_pl_acc, ru_adj_pl_inst, ru_adj_pl_prep
    );

    return switch (type) {
      case NOUN -> !hasVerbForms && !hasAdjectiveForms;
      case VERB -> !hasNounForms && !hasAdjectiveForms;
      case ADJECTIVE, PRONOUN -> !hasNounForms && !hasVerbForms;
      case ADVERB, EXPRESSION, OTHER -> !hasNounForms && !hasVerbForms && !hasAdjectiveForms;
    };
  }

  private static boolean anyNonBlank(String... values) {
    return Stream.of(values).anyMatch(value -> value != null && !value.isBlank());
  }
}
