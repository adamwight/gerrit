// Copyright (C) 2013 The Android Open Source Project
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
// limitations under the License.package com.google.gerrit.server.git;

package com.google.gerrit.server.index;

import com.google.gerrit.server.query.OperatorPredicate;

/** Index-aware predicate that includes a field type annotation. */
public abstract class IndexPredicate<I> extends OperatorPredicate<I> {
  private final FieldDef<I, ?> def;

  public IndexPredicate(FieldDef<I, ?> def, String value) {
    super(def.getName(), value);
    this.def = def;
  }

  public FieldType<?> getType() {
    return def.getType();
  }

  /**
   * @return whether this predicate can only be satisfied by looking at the
   *     secondary index, i.e. it cannot be expressed as a query over the DB.
   */
  public boolean isIndexOnly() {
    return false;
  }
}
