package org.dbiagi.marketplace.core.controller;

import javax.validation.Validation;
import javax.validation.Validator;

abstract class BaseController {
    Validator validator;

    BaseController() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
