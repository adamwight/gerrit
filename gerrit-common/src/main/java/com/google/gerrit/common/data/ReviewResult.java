// Copyright (C) 2011 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.common.data;

import com.google.gerrit.reviewdb.Change;

import java.util.ArrayList;
import java.util.List;

/**
 * Result from performing a review (comment, abandon, etc.)
 */
public class ReviewResult {
  protected List<Error> errors;
  protected Change.Id changeId;

  public ReviewResult() {
    errors = new ArrayList<Error>();
  }

  public void addError(final Error e) {
    errors.add(e);
  }

  public List<Error> getErrors() {
    return errors;
  }

  public Change.Id getChangeId() {
    return changeId;
  }

  public void setChangeId(Change.Id changeId) {
    this.changeId = changeId;
  }

  public static class Error {
    public static enum Type {
      /** Not permitted to abandon this change. */
      ABANDON_NOT_PERMITTED
    }

    protected Type type;

    protected Error() {
    }

    public Error(final Type type) {
      this.type = type;
    }

    public Type getType() {
      return type;
    }

    @Override
    public String toString() {
      return type + "";
    }
  }
}