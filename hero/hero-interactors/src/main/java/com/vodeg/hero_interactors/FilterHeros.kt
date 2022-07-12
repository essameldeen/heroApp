package com.vodeg.hero_interactors

import com.vodeg.core.FilterOrder
import com.vodeg.hero_domain.Hero
import com.vodeg.hero_domain.HeroAttribute
import com.vodeg.hero_domain.HeroFilter
import java.lang.Math.round

class FilterHeros {
    fun execute(
        currentList: List<Hero>,
        heroName: String,
        heroFilter: HeroFilter,
        heroAttribute: HeroAttribute
    ): List<Hero> {
        var filterList: MutableList<Hero> = currentList.filter {
            it.localizedName.lowercase().contains(heroName.lowercase())
        }.toMutableList()
        when (heroFilter) {
            is HeroFilter.Hero -> {
                when (heroFilter.order) {
                    is FilterOrder.Descending -> {
                        filterList.sortByDescending { it.localizedName }
                    }
                    is FilterOrder.Ascending -> {
                        filterList.sortBy { it.localizedName }
                    }
                }
            }
            is HeroFilter.ProWin -> {
                when (heroFilter.order) {
                    is FilterOrder.Descending -> {
                        filterList.sortByDescending {
                            if (it.proPick <= 0) { // can't divide by 0
                                0
                            } else {
                                val winRate: Int =
                                    round(it.proWins.toDouble() / it.proPick.toDouble() * 100).toInt()
                                winRate
                            }
                        }
                    }
                    is FilterOrder.Ascending -> {
                        filterList.sortBy {
                            if (it.proPick <= 0) { // can't divide by 0
                                0
                            } else {
                                val winRate: Int =
                                    round(it.proWins.toDouble() / it.proPick.toDouble() * 100).toInt()
                                winRate
                            }
                        }
                    }
                }
            }
        }
        when (heroAttribute) {
            is HeroAttribute.Strength -> {
                filterList = filterList.filter { it.primaryAttribute is HeroAttribute.Strength }
                    .toMutableList()
            }
            is HeroAttribute.Agility -> {
                filterList = filterList.filter { it.primaryAttribute is HeroAttribute.Agility }
                    .toMutableList()
            }
            is HeroAttribute.Intelligence -> {
                filterList = filterList.filter { it.primaryAttribute is HeroAttribute.Intelligence }
                    .toMutableList()
            }
            is HeroAttribute.Unknown -> {
                // do not filter
            }
        }

        return filterList
    }

}
