package com.akimov.domain.training.useCase.variants

class GetRightVariantIndexUseCase {
    operator fun invoke(variants: List<String>, rightVariant: String) = variants.indexOf(rightVariant)
}